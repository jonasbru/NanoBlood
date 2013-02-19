package nanoblood;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nanoblood.sound.SoundID;
import nanoblood.sound.SoundManager;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import nanoblood.ui.HeartBeatDisplay;
import nanoblood.ui.LifeDisplay;
import nanoblood.ui.ScoreDisplay;
import nanoblood.util.GameParams;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author jammers 2013
 */
public class GamePlay extends BasicGameState implements IObservable {

    private static GamePlay gp = null;

    public static GamePlay getGP() {
        return gp;
    }
    private float scrollDelta;

    class Pair<T1, T2> {

        public T1 first;
        public T2 second;
    }
    int stateID = -1;
    PhysicsObject player;
    LevelManager levelManager;
    List<PhysicsObject> objects;
    List<PhysicsObject> lasers;
    List<Splash> splashes;
    // Déclarer ses valeurs dans un properties
    float bloodSpeed = 0;
    final int bloodSpeedImpulse = 1;
    final double bloodSpeedDecrease = 0.01;
    static public final int IMPULSE_COEFF_SLOW = 6;
    static public final int IMPULSE_COEFF_MEDIUM = 8;
    static public final int IMPULSE_COEFF_HARD = 9; //20 avant
    static public final int IMPULSE_COEFF_CRAZY = 9; //30 avant
    private Vec2 gravity;
//	private BodyDef gndBodydef;
//	private Body gndBody;
//	private PolygonShape gndBox;
    private BodyDef playerBodyDef;
    private Body playerBody;
    private CircleShape playerShape;
    private FixtureDef playerFD;
    private float timeStep;
    private int velocityIterations;
    private int positionIterations;
    World world;
    int totalDistance = 0;
    float scrolledDistance = 0.0f;
    int nextDistancePopObstacle;
    int deltaDistancePopObstacle = 4;// distance between every obstacle spawn
    //Pop obstacles
    private Vec2 speedImpulse;
    private int currentHeartBeat = INITIAL_HEARTBEATS; // Current heart beats rhythm @TODO compute its average
    //* Note : Those values are heartbeat rhythms...
    static public final int INITIAL_HEARTBEATS = 50;// per minutes
    private int HEARTBEAT_THRESHOLD_CRAZY = 150;// dying soon
    private int HEARTBEAT_THRESHOLD_MEDIUM = 90;// quite excited
    private int HEARTBEAT_THRESHOLD_HARD = 120;// runner
    private int heartBeatsSinceLastUpdate = 0;
    protected static final float PIXELS_TO_METERS_RATIO = 10.0f;
    static final int OBSTACLE_SPAWN_DELAY = 300; // delay in pixels
    private static boolean DBG = true;
    private LinkedList<Long> HBList = new LinkedList<Long>();
    int score;
    float life;
    // UI elements
    ScoreDisplay scoreDisplay;
    LifeDisplay lifeDisplay;
    HeartBeatDisplay heartBeatDisplay;
    
    // Observable vars
    private boolean hasChanged;
    private ArrayList<IObserver> observers;
    
    private GameContainer lastGc;
    private StateBasedGame lastSbg;
    
    long lastTick;
    long elapsedTime;
    
    boolean laserTop = true;
    
    // Sounds
    private Sound hbSound;

    GamePlay(int stateID) {
        this.stateID = stateID;
        gp = this;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        initPhysics();
        this.player = new PhysicsObject(new Player(), playerBody);
        try {
            this.levelManager = new LevelManager(this.world);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.objects = new ArrayList<PhysicsObject>();
        this.lasers = new ArrayList<PhysicsObject>();
        this.splashes = new ArrayList<Splash>();
        
        this.objects = new ArrayList<PhysicsObject>();
        this.hasChanged = false;
        this.observers = new ArrayList<IObserver>();

        score = 0;
        life = GameParams.INSTANCE.MaxLife();

        // Create UI elements
        this.scoreDisplay = new ScoreDisplay();
        this.lifeDisplay = new LifeDisplay();
        this.heartBeatDisplay = new HeartBeatDisplay();

        // Add observers
        this.observers.add(scoreDisplay);
        this.observers.add(lifeDisplay);
        this.observers.add(heartBeatDisplay);

        // Notify for 1st time
        this.setChanged();
        this.notifyObservers();
        
        lastTick = System.currentTimeMillis();
        elapsedTime = 0;

        for (int i = 0; i < 6; i++) {
            Obstacle o = Obstacle.getRandomObstacle();
            o.setCoords(i * 200, (int) (Math.random() * Main.height));
            PhysicsObject phyObj = PhysicsObject.createFromCircSprite(o, world);
            this.objects.add(phyObj);
        }
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
//        System.out.println("ENTERING !");
        //* Pushing data to the HBQueue at the beginning so that the average on the last 10 seconds is not null or very low but is 70 HB
        java.util.Date date = new java.util.Date();
        for (int i = 0; i < ((double) INITIAL_HEARTBEATS / 60.0) * heartBeatAvgInterval + 1; i++) {
            HBList.add(date.getTime());// Note: The time is not necessarily different for each HB
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        
        this.lastGc = gc;
        this.lastSbg = sbg;
        
        this.levelManager.render(gc, sbg, grphcs);

        for (Splash s : splashes) {
            s.getRenderable().draw((float) s.getCoords().getX(), (float) s.getCoords().getY());
        }

        for (PhysicsObject so : this.objects) {
            so.getRenderable().draw((float) so.getCoords().getX(), (float) so.getCoords().getY());
        }

        for (PhysicsObject so : this.lasers) {
            so.getRenderable().draw((float) so.getCoords().getX(), (float) so.getCoords().getY());
        }

        this.player.getRenderable().draw((float) this.player.getCoords().getX(), (float) this.player.getCoords().getY());
        ((Player) player.getSprite()).getCanons().draw((float) this.player.getCoords().getX(), (float) this.player.getCoords().getY() - 4);
        if (((Player) player.getSprite()).isShieldActivated()) {
            ((Player) player.getSprite()).shield.draw((float) this.player.getCoords().getX() - 25, (float) this.player.getCoords().getY() - 25);
        }

        // UI : render last
        this.scoreDisplay.render(gc, sbg, grphcs);
        this.lifeDisplay.render(gc, sbg, grphcs);
        this.heartBeatDisplay.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        removeObjects();
        addObjects();

        manageInput(gc, sbg, delta);

        updateCurrentHB(delta);
        updatePhysics();
        updateObjects();
        updateLasers();
        updateSplashes();
        try {
            this.levelManager.update(m2px(scrollDelta), m2px(scrolledDistance));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }

        manageColisions();
        
        // Update score each second
        long currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - lastTick;
        if (elapsedTime >= 1000) {
            elapsedTime -= 1000;
            lastTick = currentTime;
            
            float scoreModifier = getScoreModifier();
            addScore((int) (GameParams.INSTANCE.ScorePerSecond() * scoreModifier));
            
            if (currentHeartBeat < GameParams.INSTANCE.BeatThreshold1()) {
                addLife(-GameParams.INSTANCE.DamageLowBeat());
            } else if (currentHeartBeat > GameParams.INSTANCE.BeatThreshold4()) {
                addLife(-GameParams.INSTANCE.DamageHighBeat());
            }
        }

        if(player.getCoords().getY() < 0 || player.getBody().getPosition().y < 0 || player.getSprite().coords.getY() < 0) {
            player.getBody().getPosition().y = 0;
            player.getSprite().setCoords((int)player.getSprite().getCoords().getX(), 0);
        } else if(player.getCoords().getY() > Main.height || player.getBody().getPosition().y > Main.height || player.getSprite().coords.getY() > Main.height) {
            player.getBody().getPosition().y = Main.height;
            player.getSprite().setCoords((int)player.getSprite().getCoords().getX(), Main.height);
        }

        if (life <= 0) {
//            SoundManager.INSTANCE.playAsSoundEffect(SoundID.GAMEOVER, false);
//            GameParams.INSTANCE.setLastScore(score);
//            this.lastSbg.enterState(Main.GAMEOVER);
        }
    }

    private float computeImpulseFromHeartBeat(int hb) {
        int coeff;
        if (currentHeartBeat > HEARTBEAT_THRESHOLD_CRAZY) {
            coeff = IMPULSE_COEFF_CRAZY;
        } else if (currentHeartBeat > HEARTBEAT_THRESHOLD_HARD) {
            coeff = IMPULSE_COEFF_HARD;
        } else if (currentHeartBeat > HEARTBEAT_THRESHOLD_MEDIUM) {
            coeff = IMPULSE_COEFF_MEDIUM;
        } else {
            coeff = IMPULSE_COEFF_SLOW;
        }

        float result = coeff * currentHeartBeat;

        return result;
    }

    private void manageInput(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_Z) || input.isKeyDown(Input.KEY_W)) {
            player.moveImpulse(Player.upImpulseVec);
            ((Player) player.getSprite()).goUp();
        } else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            player.moveImpulse(Player.downImpulseVec);
            ((Player) player.getSprite()).goDown();
        } else {
            ((Player) player.getSprite()).stop();
        }

        if (input.isKeyPressed(Input.KEY_SPACE)) { // HEARTBEAT
            playerHeartBeat(delta);
            
            SoundManager.INSTANCE.playAsSoundEffect(SoundID.HEARTBEAT, 1f, 4f, false);
        }

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_LCONTROL)) {
            Laser l = new Laser();
            SoundManager.INSTANCE.playAsSoundEffect(SoundID.LASER1, 1f, 0.2f, false);
            if(laserTop) {
                l.setCoords((int) player.getCoords().getX() + (int) scrolledDistance * 10, (int) player.getCoords().getY() - 27);
            } else {
                l.setCoords((int) player.getCoords().getX() + (int) scrolledDistance * 10, (int) player.getCoords().getY() + 24);
            }
            laserTop = !laserTop;

            PhysicsObject phyObj = PhysicsObject.createFromCircSprite(l, world);

//            phyObj.moveImpulse(new Vec2(150.0f, 0.0f));
            //phyObj.getBody().applyLinearImpulse(new Vec2(500, 0.0f), phyObj.getBody().getPosition());

            lasers.add(phyObj);
        }

        // Update HB display
        this.setChanged();
        notifyObserver(heartBeatDisplay);
        
    }

    private void updateObjects() {
        List<PhysicsObject> toRemove = new ArrayList<PhysicsObject>();

        for (PhysicsObject so : this.objects) {
            so.move(new Vec2(0.0f, 0.0f));
            if (so.getSprite() instanceof Cancer) {
                Vec2 v = player.getPhyCoordsVec().sub(so.getPhyCoordsVec());
                
                v.normalize();
                if (DBG) {
                    System.out.println("Normalized cancer v-vect=" + v);
                }

                so.move((Cancer.MOVEMENT_TO_PLAYER * v.x), (Cancer.MOVEMENT_TO_PLAYER * v.y));
            }

            if (so.getCoords().getX() < -250) {
                toRemove.add(so);
            }
        }

        for (PhysicsObject so : toRemove) {
            this.objects.remove(so);
        }
    }

    private void manageColisions() {

        for (int i = 0; i < this.objects.size(); i++) {
            if (this.player.getSprite().boundingBox.intersects(objects.get(i).getSprite().getBoundingBox())) {
                objects.get(i).colideWithPlayer();
            }
        }

        for (int j = 0; j < this.lasers.size();) {
            boolean stop = false;
            for (int i = 0; i < this.objects.size();) {
                stop = false;
                Sprite s = this.objects.get(i).getSprite();
                Laser l = (Laser) this.lasers.get(j).getSprite();
                if (s.boundingBox.intersects(l.getBoundingBox())
                        && (s instanceof Cancer || s instanceof GlobuleBlanc || s instanceof GlobuleRouge || s instanceof Virus)) {
                    ((Obstacle) s).die();
                    objects.remove(i);
                    lasers.remove(j);
                    stop = true;
                    break;
                } else {
                    i++;
                }
            }
            if(!stop) {
                j++;
            }
        }


    }

    private void removeObjects() {
        for (int i = 0; i < this.objects.size();) {
            if (this.objects.get(i).getSprite().needToRemove()) {
                this.objects.remove(i);
            } else {
                i++;
            }
        }
    }

    private void addObjects() throws SlickException {
        if (scrolledDistance > nextDistancePopObstacle) {
            spawnRandomObject();
            nextDistancePopObstacle += deltaDistancePopObstacle;
        }
    }

    private void initPhysics() {
        gravity = new Vec2(0.0f, 0.0f);
        world = new World(gravity, true);
        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyType.DYNAMIC;
        playerBodyDef.position.x = px2m(Main.PLAYER_X);
        playerBodyDef.position.y = px2m((int) ySlick2Physics(Player.INIT_Y));
        playerBody = world.createBody(playerBodyDef);
        playerShape = new CircleShape();
//        playerShape = new PolygonShape();
//        playerShape.setAsBox(px2m(Player.WIDTH/2), px2m(Player.HEIGHT/2));
        playerShape.m_radius = 1.0f;
        playerShape.m_type = ShapeType.CIRCLE;
        playerFD = new FixtureDef();
        playerFD.shape = playerShape;
        playerFD.density = 1.0f;
        //* The two next lines together allow us to have friction against a number of circles even if we are a circle ourself
        playerFD.friction = 1.5f;
        playerBody.setAngularDamping(200);
        playerBody.setLinearDamping(1.2f);
        playerBody.createFixture(playerFD);
        
        timeStep = 1.0f / 60.0f;
        velocityIterations = 6;
        positionIterations = 2;
    }

    static public float ySlick2Physics(float y) {
        return Main.height - y;
    }

    public static float yFromPhysicsToSlick(float y) {
        return Main.height - y;
    }

    private void updatePhysics() {
        Vec2 currPos = new Vec2(player.getPhyCoordsVec());
        world.step(timeStep, velocityIterations, positionIterations);
        scrollDelta = player.getPhyCoordsVec().x - currPos.x;
        scrolledDistance += scrollDelta;
        
        if (DBG) {
            System.out.println("Player=" + player.getPhyCoordsVec() + "\t" + player.getCoords());
            System.out.println("Scrolled=" + scrolledDistance);
        }
        PhysicsObject.setScrolledDistance(scrolledDistance);
        if (DBG) {
            for (PhysicsObject po : objects) {
                System.out.println(po.getPhyCoords() + "\t" + po.getCoords());
            }
        }
    }

    /**
     * Pixels to Meters converter
     * @param px
     * @return
     */
    public static float px2m(int px) {
        return px / PIXELS_TO_METERS_RATIO;
    }

    /**
     * Meters to pixels converter
     * @param m
     * @return
     */
    public static int m2px(float m) {
        return (int) (m * PIXELS_TO_METERS_RATIO);
    }
    private int heartBeatAvgInterval = 5; // In seconds

    private void updateCurrentHB(int delta) {
        // Computing average:
        Long tenSecondsAgo = new Date().getTime() - heartBeatAvgInterval * 1000;
        int sum = 0; // sum of the HB in the last 10 seconds
        for (int i = 0; i < HBList.size();) {// until the end of the list (/!\ the list is changed inside the loop)
            Long l = HBList.get(i);
            if (l.compareTo(tenSecondsAgo) < 0) {// if too old
                HBList.remove(i);// remove and DO NOT increment i
            } else { // else, take into account and go to next (increment i)
                i++;
                sum++;
            }
        }
        currentHeartBeat = (int) ((double) sum / (double) (heartBeatAvgInterval) * 60.0);//Average on {heartBeatAvgInterval} seconds, that we put on a 60seconds basis
        if (DBG) {
            System.out.println("currentHeartBeat=" + currentHeartBeat);
        }
    }

    /**
     * This method is executed in manageInput() everytime a heartbeat input from the player is received
     * @param delta
     */
    private void playerHeartBeat(int delta) {
        speedImpulse = new Vec2(computeImpulseFromHeartBeat(currentHeartBeat), 0.0f);
        playerBody.applyLinearImpulse(speedImpulse, playerBody.getPosition());
        java.util.Date date = new java.util.Date();
        HBList.add(date.getTime());// Adding the new HB to the list of HB from the player
    }

    public float getScoreModifier() {
        if (currentHeartBeat <= GameParams.INSTANCE.BeatThreshold1()) {
            return GameParams.INSTANCE.ScoreModifier1();
        } else if (currentHeartBeat > GameParams.INSTANCE.BeatThreshold1()
                && currentHeartBeat <= GameParams.INSTANCE.BeatThreshold2()) {
            return GameParams.INSTANCE.ScoreModifier2();
        } else if (currentHeartBeat > GameParams.INSTANCE.BeatThreshold2()
                && currentHeartBeat <= GameParams.INSTANCE.BeatThreshold3()) {
            return GameParams.INSTANCE.ScoreModifier3();
        } else if (currentHeartBeat > GameParams.INSTANCE.BeatThreshold3()
                && currentHeartBeat <= GameParams.INSTANCE.BeatThreshold4()) {
            return GameParams.INSTANCE.ScoreModifier4();
        } else {
            return GameParams.INSTANCE.ScoreModifier5();
        }
    }

    private void updateLasers() {
        for (PhysicsObject po : this.lasers) {
//            po.getCoords().setLocation(po.getCoords().getX() + 10, po.getCoords().getY());
//            po.getSprite().coords.setLocation(po.getCoords().getX() + 10, po.getCoords().getY());
            po.getBody().setTransform(new Vec2((float) po.getBody().getPosition().x + 3, (float) po.getBody().getPosition().y), 0);
        }
    }

    private void updateSplashes() {
        for (int i = 0; i < this.splashes.size(); i++) {
            if (splashes.get(i).staticA.isStopped()) {
                this.splashes.remove(i);
            } else {
                i++;
            }
        }
    }

    public void addLife(int dLife) {
        life += dLife;
        if (life < 0) {
            life = 0;
        }
        else if (life > GameParams.INSTANCE.MaxLife()) {
            life = GameParams.INSTANCE.MaxLife();
        }
        
        setChanged();
        notifyObserver(lifeDisplay);
    }

    public void addScore(int dScore) {
        score += dScore;
        setChanged();
        notifyObserver(scoreDisplay);
    }

    private void spawnRandomObject() throws SlickException {
        Obstacle o = Obstacle.getRandomObstacle();
        o.setCoords(Main.width + OBSTACLE_SPAWN_DELAY + m2px(scrolledDistance), (int) (Math.random() * Main.height));
//        o.setCoords(4000, (int) (Math.random() * Main.height));
        PhysicsObject phyObj = PhysicsObject.createFromCircSprite(o, world);
        //phyObj.move(3.0f, 0.0f);
        this.objects.add(phyObj);
    }

    public int getScore() {
        return score;
    }

    public float getLife() {
        return life;
    }

    public float getHeartBeat() {
        return bloodSpeed;
    }

    public int getCurrentHeartBeat() {
        return currentHeartBeat;
    }

    // --- Observer methods
    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public int countObservers() {
        return observers.size();
    }

    @Override
    public void deleteObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void deleteObservers(IObserver o) {
        observers.clear();
    }

    @Override
    public boolean hasChanged() {
        return hasChanged;
    }

    @Override
    public void notifyObserver(IObserver o) {
        if (hasChanged() && observers.indexOf(o) != -1) {
            observers.get(observers.indexOf(o)).update(this, null);
            clearChanged();
        }
    }

    @Override
    public void notifyObservers() {
        if (hasChanged()) {
            for (IObserver obs : observers) {
                obs.update(this, null);
            }
            clearChanged();
        }
    }

    protected void clearChanged() {
        hasChanged = false;
    }

    public void setChanged() {
        hasChanged = true;
    }

    static float px2m(double x) {
        return px2m((int) x);
    }
}

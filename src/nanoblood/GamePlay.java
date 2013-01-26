/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.util.ArrayList;
import java.util.List;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author jonas
 */
public class GamePlay extends BasicGameState {

    int stateID = -1;
    Player player;
    LevelManager levelManager;
    List<StaticObject> objects;
    float bloodSpeed = 0;
    final int bloodSpeedImpulse = 3;
    final double bloodSpeedDecrease = 0.01;

    GamePlay(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.player = new Player();
        this.levelManager = new LevelManager();
        this.objects = new ArrayList<StaticObject>();

        for (int i = 0; i < 200; i++) {
            Obstacle o = new Obstacle();
            o.setCoords(i * 200, (int) (Math.random() * Main.height));
            this.objects.add(o);
        }
		
		initPhysics();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        this.levelManager.render(gc, sbg, grphcs);

        for (StaticObject so : this.objects) {
            so.getRenderable().draw((float) so.coords.getX(), (float) so.coords.getY());
        }

        this.player.getRenderable().draw(Main.width / 2, (float) this.player.getCoords().getY());
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        removeObjects();

        manageInput(gc, sbg, i);

        updateObjects();

        this.levelManager.update(this.bloodSpeed);
    }

    private void manageInput(GameContainer gc, StateBasedGame sbg, int delta) {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_UP)) {
            player.goUp();
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            player.goDown();
        } else {
            player.stop();
        }

        if (input.isKeyPressed(Input.KEY_SPACE)) {
            this.bloodSpeed += this.bloodSpeedImpulse;
        } else {
            this.bloodSpeed -= this.bloodSpeedDecrease * this.bloodSpeed;
            if (this.bloodSpeed < 0) {
                this.bloodSpeed = 0;
            }
        }
    }

    private void updateObjects() {
        List<StaticObject> toRemove = new ArrayList<StaticObject>();

        for (StaticObject so : this.objects) {
            so.move((int) -this.bloodSpeed, 0);
            if (so.coords.getX() < -50) {
                toRemove.add(so);
            }
        }

        for (StaticObject so : toRemove) {
            this.objects.remove(so);
        }

        // WIP : FX
//        if (bloodSpeed < 100) {
//            float alpha = 1 * (100 - bloodSpeed) / (100);
//            System.out.println(bloodSpeed + " -> alpha = " + alpha);
//            levelManager.setBlackFxAlpha(alpha);
//        }
    }
    
    private void manageColisions() {
        for(StaticObject so : this.objects) {
            if(this.player.boundingBox.intersects(so.getBoundingBox())) {

            }
        }
    }

    private void removeObjects() {
        for(int i = 0; i < this.objects.size();) {
            if(this.objects.get(i).needToRemove()) {
                this.objects.remove(i);
            } else {
                i++;
            }
        }

    }

	private void initPhysics() {
		Vec2 gravity = new Vec2(10.0f, 0);
		World world = new World(gravity, true);
		BodyDef gndBodydef = new BodyDef();
		gndBodydef.position.set(0.0f, (float)(0.2 * Main.width));
		Body gndBody = world.createBody(gndBodydef);
		PolygonShape gndBox = new PolygonShape();
		gndBox.setAsBox(10.0f, (float)Main.width);
		gndBody.createFixture(gndBox, 0.0f);
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DYNAMIC;
		playerBodyDef.position.x = (float)Main.width / 2;
		playerBodyDef.position.y = (float)this.player.getCoords().getY();
		Body playerBody = world.createBody(playerBodyDef);
		PolygonShape playerShape = new PolygonShape();
		playerShape.setAsBox(this.player.getWidth()/2, this.player.getHeight()/2);
		FixtureDef playerFD = new FixtureDef();
		playerFD.shape = playerShape;
		playerFD.density = 1.0f;
		playerFD.friction =  0.3f;
	}
}

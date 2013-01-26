/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.util.ArrayList;
import java.util.List;
import nanoblood.ui.LifeDisplay;
import nanoblood.ui.ScoreDisplay;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
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
public class GamePlay extends BasicGameState implements IObservable {

    int stateID = -1;
    Player player;
    LevelManager levelManager;
    List<StaticObject> objects;
    float bloodSpeed = 0;
    final int bloodSpeedImpulse = 3;
    final double bloodSpeedDecrease = 0.01;
    int totalDistance = 0;
    int nextDistancePopObstacle;
    int deltaDistancePopObstacle = 200;
    private int score;
    private float life;
    
    // TODO timer

    // UI elements
    ScoreDisplay scoreDisplay;
    LifeDisplay lifeDisplay;
    
    // Observable vars
    private boolean hasChanged;
    private ArrayList<IObserver> observers;
    
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
        
        this.hasChanged = false;
        this.observers = new ArrayList<IObserver>();
        
        // TODO declarer parametres dans un fichier properties
        score = 0;
        life = 100;
        
        // Create UI elements
        this.scoreDisplay = new ScoreDisplay();
        this.lifeDisplay = new LifeDisplay();
        
        // Add observers
        this.observers.add(scoreDisplay);
        this.observers.add(lifeDisplay);
        
        // Notify for 1st time
        this.setChanged();
        this.notifyObservers();

        nextDistancePopObstacle = Main.width + deltaDistancePopObstacle;

        player.setCoords(200, Main.height / 2);

        for (int i = 0; i < 6; i++) {
            Obstacle o = Obstacle.getRandomObstacle();
            o.setCoords(i * 200, (int) (Math.random() * Main.height));
            this.objects.add(o);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        this.levelManager.render(gc, sbg, grphcs);

        for (StaticObject so : this.objects) {
            so.getRenderable().draw((float) so.coords.getX(), (float) so.coords.getY());
        }

        this.player.getRenderable().draw((float) this.player.getCoords().getX(), (float) this.player.getCoords().getY());
        this.player.getCanons().draw((float) this.player.getCoords().getX(), (float) this.player.getCoords().getY() - 4);
        
        // UI : render last
        this.scoreDisplay.render(gc, sbg, grphcs);
        this.lifeDisplay.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        removeObjects();
        addObjects();

        manageInput(gc, sbg, i);

        updateObjects();

        this.levelManager.update(this.bloodSpeed);

        manageColisions();
        
        if (life <= 0) {
            // TODO game over screen
        }
        
        // DEBUG score
        score = (int) bloodSpeed;
        setChanged();
        notifyObserver(scoreDisplay);
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
        
        // DEBUG score
        if (input.isKeyPressed(Input.KEY_TAB)) {
            score += 100;
            setChanged();
            notifyObserver(scoreDisplay);
        }
        
        // DEBUG life
        if (input.isKeyPressed(Input.KEY_A)) {
            if (life > 0) {
                life -= 10;
            }
            setChanged();
            notifyObserver(lifeDisplay);
        }

        totalDistance += bloodSpeed;
    }

    private void updateObjects() {
        List<StaticObject> toRemove = new ArrayList<StaticObject>();

        for (StaticObject so : this.objects) {
            so.move((int) -this.bloodSpeed / 3, 0);
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
                so.colideWithPlayer();
                
                // TODO update life value
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

    private void addObjects() throws SlickException {
        if(totalDistance > nextDistancePopObstacle) {
            Obstacle o = Obstacle.getRandomObstacle();
            o.setCoords(Main.width + 300 - (totalDistance - nextDistancePopObstacle), (int) (Math.random() * Main.height));
            this.objects.add(o);
            nextDistancePopObstacle += deltaDistancePopObstacle;
        }
    }
    
    public int getScore() {
        return score;
    }
    
    public float getLife() {
        return life;
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
    
    protected void setChanged() {
        hasChanged = true;
    }
    
    
}

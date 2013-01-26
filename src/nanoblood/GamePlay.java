/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.util.ArrayList;
import java.util.List;
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
    int totalDistance = 0;
    int nextDistancePopObstacle;
    int deltaDistancePopObstacle = 200;

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

        nextDistancePopObstacle = Main.width + deltaDistancePopObstacle;

        player.setCoords(200, Main.height / 2);

        for (int i = 0; i < 6; i++) {
            Obstacle o = new Obstacle();
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
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        removeObjects();
        addObjects();

        manageInput(gc, sbg, i);

        updateObjects();

        //TODO : lag, tofix
        this.levelManager.update(this.bloodSpeed);

        manageColisions();
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

        totalDistance += bloodSpeed;
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
                so.colideWithPlayer();
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
            Obstacle o = new Obstacle();
            o.setCoords(Main.width + 300 - (totalDistance - nextDistancePopObstacle), (int) (Math.random() * Main.height));
            this.objects.add(o);
            nextDistancePopObstacle += deltaDistancePopObstacle;
        }
    }
}

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
    List<StaticObject> objects;
    double bloodSpeed = 0;
    final int bloodSpeedImpulse = 5;
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
        this.objects = new ArrayList<StaticObject>();

        for (int i = 0; i < 200; i++) {
            Obstacle o = new Obstacle();
            o.setCoords(i * 200, (int) (Math.random() * Main.height));
            this.objects.add(o);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        this.player.getRenderable().draw(Main.width / 2, (float) this.player.getCoords().getY());


        for (StaticObject so : this.objects) {
            so.getRenderable().draw((float) so.coords.getX(),(float) so.coords.getY());
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        manageInput(gc, sbg, i);
        updateObjects();
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
            if(this.bloodSpeed < 0) {
                this.bloodSpeed = 0;
            }
        }
        
        System.out.println("plop " + bloodSpeed);
    }

    private void updateObjects() {
        List<StaticObject> toRemove = new ArrayList<StaticObject>();

        for (StaticObject so : this.objects) {
            so.move(-this.bloodSpeed, 0);
            if (so.coords.getX() < 50) {
                toRemove.add(so);
            }
        }

        for(StaticObject so : toRemove) {
            this.objects.remove(so);
        }
    }
}

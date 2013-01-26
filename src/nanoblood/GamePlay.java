/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

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
    int scrollSpeed = 0;

    GamePlay(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.player = new Player();
        this.levelManager = new LevelManager();
        this.scrollSpeed = 0;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        
        this.levelManager.render(gc, sbg, grphcs);
        
        this.player.getRenderable().draw(Main.width / 2, (float) this.player.getCoords().getY());
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
        if (scrollSpeed > 0) {
            scrollSpeed--;
        }
        
        this.levelManager.update(this.scrollSpeed);
        
        manageInput(gc, sbg, i);
    }

    private void manageInput(GameContainer gc, StateBasedGame sbg, int delta) {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_UP)) {
            player.goUp();
        }

        if (input.isKeyDown(Input.KEY_DOWN)) {
            player.goDown();
        }

        if (input.isKeyDown(Input.KEY_SPACE)) {
            scrollSpeed += 3;
        }
        
    }
}

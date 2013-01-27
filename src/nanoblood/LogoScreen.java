/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author jonas
 */
public class LogoScreen extends BasicGameState {

    private int stateID = -1;
    private Image bg;

    LogoScreen(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = Sprite.getImage("sprites/TITLE_SCREEN.png");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        bg.draw();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(Main.MAINMENU);
        }
    }
}

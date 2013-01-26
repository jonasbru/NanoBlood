package nanoblood;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author troll
 */
public class Tutorial extends BasicGameState {
	private int stateID = -1;
	private Image bg;

	Tutorial(int stateID) {
		 this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = Sprite.getImage("sprites/tutorial.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
		bg.draw();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		// who cares
	}
	
}
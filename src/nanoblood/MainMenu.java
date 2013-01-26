/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author jammerzzz
 */
public class MainMenu extends BasicGameState implements ComponentListener {

    int stateID = -1;

    Image bg;
	private MouseOverArea soundCurrent, soundOn, soundOff;
	protected static boolean SOUND_ON = true;
	private MouseOverArea play;
	private Image soundOffImg;
	private Image soundOnImg;
	private GameContainer lastgc;
   

    MainMenu(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("sprites/main_menu.jpg");
		soundOffImg = Sprite.getImage("sprites/sound_menu_OFF.png");
		soundOnImg = Sprite.getImage("sprites/sound_menu_ON.png");
		soundOn = new MouseOverArea(gc, soundOnImg, (int)(0.8 * Main.width), (int) (0.8 * Main.height), this);
		soundOff = new MouseOverArea(gc, soundOffImg, (int)(0.8 * Main.width), (int) (0.8 * Main.height), this);
		soundCurrent = soundOn;//By default, sound is activated
		play = new MouseOverArea(gc, Sprite.getImage("sprites/play.png"), (int)(0.3 * Main.width), (int)(0.3*Main.height), this);
		play.setMouseOverImage(Sprite.getImage("sprites/play_mouseover.png"));
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        bg.draw(0, 0);
        soundCurrent.render(gc, grphcs);
		play.render(gc, grphcs);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		this.lastgc = gc;
    }

	public void componentActivated(AbstractComponent source) {
		if (soundCurrent == source) {
			if (SOUND_ON) {
				SOUND_ON = false;
				soundCurrent = soundOff;
			} else {
				SOUND_ON = true;
				soundCurrent = soundOn;
			}
		}
	}

}
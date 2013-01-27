/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
    
    private Sound playSound;

    Image bg;
	private MouseOverArea soundCurrent, soundOn, soundOff;
	protected static boolean SOUND_ON = true;
	private MouseOverArea play, tuto;
	private Image soundOffImg;
	private Image soundOnImg;
	private GameContainer lastgc;
	private StateBasedGame lastsbg;
	private Image bg2;
	private boolean lightOn = false;
   

    MainMenu(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("sprites/main_menu.jpg");
		bg2 = new Image("sprites/little_lights.png");
		soundOffImg = Sprite.getImage("sprites/sound_menu_OFF.png");
		soundOnImg = Sprite.getImage("sprites/sound_menu_ON.png");
		soundOn = new MouseOverArea(gc, soundOnImg, (int)(0.8 * Main.width), (int) (0.8 * Main.height), this);
		soundOff = new MouseOverArea(gc, soundOffImg, (int)(0.8 * Main.width), (int) (0.8 * Main.height), this);
		soundCurrent = soundOn;//By default, sound is activated
		play = new MouseOverArea(gc, Sprite.getImage("sprites/play.png"), 575, 180, this);
		play.setMouseOverImage(Sprite.getImage("sprites/play_mouseover.png"));
		tuto = new MouseOverArea(gc, Sprite.getImage("sprites/tuto.png"), 192, 103, this);
		tuto.setMouseOverImage(Sprite.getImage("sprites/tuto_mouseover.png"));
                
        playSound = new Sound("sounds/sfx/play.mp3");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        bg.draw(0, 0);
        soundCurrent.render(gc, grphcs);
		play.render(gc, grphcs);
		tuto.render(lastgc, grphcs);
		if (lightOn) {
			bg2.draw(0, 0);
		}
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		this.lastgc = gc;
		this.lastsbg = sbg;
		if (play.isMouseOver() || tuto.isMouseOver()) {
			lightOn = true;
		} else {
			lightOn = false;
		}
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
		} else if(play == source) {// if "Play" button is clicked
                    playSound.play();
			this.lastsbg.enterState(Main.GAMEPLAY);//go to GamePlay state
		} else if(tuto == source) {
			this.lastsbg.enterState(Main.TUTORIAL);
		}
	}

}
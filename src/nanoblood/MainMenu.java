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
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author jonas
 */
public class MainMenu extends BasicGameState implements ComponentListener {

    int stateID = -1;

    Image bg;
	private CustomMouseOverArea sound;
	protected static boolean SOUND_ON = true;
   

    MainMenu(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("sprites/main_menu.jpg");
		sound = new CustomMouseOverArea(gc, Sprite.getImage("sprites/sound_menu_ON.png"), (int)0.8 * Main.width, (int) 0.8 * Main.height, this);
		sound.setSelectedImage(Sprite.getImage("sprites/sound_menu_ON.png"));
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        bg.draw(0, 0);
        sound.render(gc, grphcs);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
    }

	public void componentActivated(AbstractComponent source) {
		if (sound == source) {
			SOUND_ON = false;
		}
	}

}
class CustomMouseOverArea extends MouseOverArea {

    private boolean selected = false;
	private Image img;
	private Image selectedImg;

    public CustomMouseOverArea(GUIContext container, Image image, int x, int y, ComponentListener listener) {
        super(container, image, x, y, listener);
		this.img = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
	
	public void setSelectedImage(Image i) {
		this.selectedImg = i;
	}
	
	public void setImage(Image i) {
		this.img = i;
	}
	
	public Image getImage() {
		if(this.selected) {
			return this.selectedImg;
		} else {
			return this.img;
		}
	}
}
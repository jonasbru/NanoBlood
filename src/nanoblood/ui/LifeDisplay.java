/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.ui;

import nanoblood.GamePlay;
import nanoblood.Sprite;
import nanoblood.util.GameParams;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Anthony
 */
public class LifeDisplay implements IObserver{

    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    
    public static final int CONTENT_WIDTH = 290;
    public static final int CONTENT_HEIGHT = 34;
    public static final int CONTENT_X_OFFSET = 96;
    public static final int CONTENT_Y_OFFSET = 11;
    
    private float life;
    private Image lifeBarBackground;
    
    public LifeDisplay() throws SlickException {
        this.life = 0f;
        lifeBarBackground = Sprite.getImage("sprites/ui/LifeBar.png");
    }

    @Override
    public void update(IObservable obs, Object obj) {
        if (obs instanceof GamePlay) {
            life = ((GamePlay)obs).getLife();
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        
        // bar BG
        lifeBarBackground.draw(X_OFFSET, Y_OFFSET);
        
        // black rect mask
        int maxLife = GameParams.INSTANCE.MaxLife();
        
        if (life >= 0 && life < maxLife) {
            int width = (int) ((maxLife - life) * CONTENT_WIDTH / maxLife);
            
            gr.setColor(Color.black);
            gr.fillRect(X_OFFSET + CONTENT_X_OFFSET + CONTENT_WIDTH - width, 
                    Y_OFFSET + CONTENT_Y_OFFSET, 
                    width, 
                    CONTENT_HEIGHT);
        }
    }
    
}

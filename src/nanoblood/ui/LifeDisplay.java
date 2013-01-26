/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.ui;

import nanoblood.GamePlay;
import nanoblood.Main;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Anthony
 */
public class LifeDisplay implements IObserver{

    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    
    public static final int BAR_WIDTH = 300;
    public static final int BAR_HEIGHT = 30;
    public static final int LIFE_MIN = 0; // get values from properties (or Gameplay)
    public static final int LIFE_MAX = 100;
    
    private float life;
    
    public LifeDisplay() {
        this.life = 0f;
    }

    @Override
    public void update(IObservable obs, Object obj) {
        if (obs instanceof GamePlay) {
            life = ((GamePlay)obs).getLife();
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        
        gr.setColor(Color.black);
        gr.fillRect(X_OFFSET, Y_OFFSET, BAR_WIDTH, BAR_HEIGHT);
        
        if (life > 0) {
            int width = (int) (life * BAR_WIDTH / LIFE_MAX);
            
            gr.setColor(Color.green);
            gr.fillRect(X_OFFSET, Y_OFFSET, width, BAR_HEIGHT);
        }
        
    }
    
}

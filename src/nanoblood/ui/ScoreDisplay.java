/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.ui;

import java.awt.Font;
import nanoblood.GamePlay;
import nanoblood.Main;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Anthony
 */
public class ScoreDisplay implements IObserver {

    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    
    int score;
    UnicodeFont font;
    
    public ScoreDisplay() throws SlickException {
        score = 0;
        
        font = new UnicodeFont(new Font(Font.SANS_SERIF, Font.ITALIC, 72)); // TODO choisir font
        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        font.getEffects().add(new OutlineEffect(4, new java.awt.Color(0, 255, 100, 128)));
        font.addGlyphs("0123456789 pts");
        font.loadGlyphs();
        
        
        
    }

    @Override
    public void update(IObservable obs, Object obj) {
        if (obs instanceof GamePlay) {
            score = ((GamePlay)obs).getScore();
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        String scoreText = score + " pts";
        int textWidth = font.getWidth(scoreText);
        
        gr.setColor(Color.white);
        gr.setFont(font);
        // TODO outline effect
        gr.drawString(scoreText, Main.width - X_OFFSET - textWidth, Y_OFFSET);
        
    }
    
}

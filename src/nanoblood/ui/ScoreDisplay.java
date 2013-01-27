/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
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
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Anthony
 */
public class ScoreDisplay implements IObserver {

    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    
    private int score;
    private UnicodeFont unicodeFont;
    
    public ScoreDisplay() throws SlickException {
        score = 0;
        
        Font javaFont = null;
        try {
            javaFont = Font.createFont(Font.TRUETYPE_FONT, 
         ResourceLoader.getResourceAsStream("fonts/LCD_Mono_Normal.ttf"));
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        unicodeFont = new UnicodeFont(javaFont, 72, false, false);
        unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        unicodeFont.getEffects().add(new OutlineEffect(4, new java.awt.Color(0, 255, 100, 64)));
        unicodeFont.addGlyphs("0123456789 pts");
        unicodeFont.loadGlyphs();
    }

    @Override
    public void update(IObservable obs, Object obj) {
        if (obs instanceof GamePlay) {
            score = ((GamePlay)obs).getScore();
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        String scoreText = score + " pts";
        int textWidth = unicodeFont.getWidth(scoreText);
        
        gr.setColor(Color.white);
        gr.setFont(unicodeFont);
        gr.drawString(scoreText, Main.width - X_OFFSET - textWidth, Y_OFFSET);
        
    }
    
}

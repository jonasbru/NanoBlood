/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import nanoblood.util.GameParams;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;


/**
 *
 * @author Anthony
 */
public class GameOver extends BasicGameState {

    int stateID = -1;
    
    Image bg;
    private GameContainer lastgc;
    private StateBasedGame lastsbg;
    
    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    
    private int score;
    private UnicodeFont unicodeFont;
   

    GameOver(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("sprites/game_over.jpg");
        
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
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        this.lastgc = gc;
        this.lastsbg = sbg;
        
        bg.draw(0, 0, Main.width, Main.height);
        
        
        score = GameParams.INSTANCE.getLastScore();
        String scoreText = score + " pts";
        int textWidth = unicodeFont.getWidth(scoreText);
        
        gr.setColor(Color.white);
        gr.setFont(unicodeFont);
        gr.drawString(scoreText, Main.width - X_OFFSET - textWidth, Y_OFFSET);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            //this.lastsbg.enterState(Main.MAINMENU);
            System.exit(0);
        }
    }    
}
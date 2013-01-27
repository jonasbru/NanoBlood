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
import nanoblood.Sprite;
import nanoblood.util.GameParams;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
public class HeartBeatDisplay implements IObserver {

    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    
    public static final int CONTENT_WIDTH = 209;
    public static final int CONTENT_HEIGHT = 21;
    public static final int CONTENT_X_OFFSET = 83;
    public static final int CONTENT_Y_OFFSET = 11;
    
    private float heartBeat;
    private Image heartBeatBackground;
    private Image cursorImage;
    private float scoreModifier;
    private UnicodeFont unicodeFont;
    
    private int cursor1;
    private int cursor2;
    private int cursor3;
    private int cursor4;

    public HeartBeatDisplay() throws SlickException {
        this.heartBeat = 0f;
        heartBeatBackground = Sprite.getImage("sprites/ui/HeartBar.png");
        cursorImage = Sprite.getImage("sprites/ui/HeartBarCursor.png");
        scoreModifier = 0f;
        
        Font javaFont = null;
        try {
            javaFont = Font.createFont(Font.TRUETYPE_FONT, 
         ResourceLoader.getResourceAsStream("fonts/LCD_Mono_Normal.ttf"));
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        unicodeFont = new UnicodeFont(javaFont, 32, false, false);
        unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        unicodeFont.getEffects().add(new OutlineEffect(3, new java.awt.Color(0, 255, 100, 64)));
        unicodeFont.addGlyphs("0123456789 x.");
        unicodeFont.loadGlyphs();
        
        cursor1 = (int) (X_OFFSET + CONTENT_X_OFFSET + 
                GameParams.INSTANCE.BeatThreshold1() *
                CONTENT_WIDTH / GameParams.INSTANCE.MaxBeat());
        cursor2 = (int) (X_OFFSET + CONTENT_X_OFFSET + 
                GameParams.INSTANCE.BeatThreshold2() *
                CONTENT_WIDTH / GameParams.INSTANCE.MaxBeat());
        cursor3 = (int) (X_OFFSET + CONTENT_X_OFFSET + 
                GameParams.INSTANCE.BeatThreshold3() *
                CONTENT_WIDTH / GameParams.INSTANCE.MaxBeat());
        cursor4 = (int) (X_OFFSET + CONTENT_X_OFFSET + 
                GameParams.INSTANCE.BeatThreshold4() *
                CONTENT_WIDTH / GameParams.INSTANCE.MaxBeat());
        
        
    }

    @Override
    public void update(IObservable obs, Object obj) {
        if (obs instanceof GamePlay) {
            heartBeat = ((GamePlay) obs).getCurrentHeartBeat();
            scoreModifier = ((GamePlay) obs).getScoreModifier();
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {

        // bar BG
        heartBeatBackground.draw(X_OFFSET, Main.height - 40 - Y_OFFSET);
        
        int y = Main.height - 40 - Y_OFFSET + CONTENT_Y_OFFSET;

        if (heartBeat >= 0 && heartBeat <= GameParams.INSTANCE.MaxBeat()) {
            int hbCursorPos = (int) (heartBeat * CONTENT_WIDTH / GameParams.INSTANCE.MaxBeat());
            

            gr.setColor(new Color(0, 255, 100));
            gr.drawLine(X_OFFSET + CONTENT_X_OFFSET + hbCursorPos,
                    y,
                    X_OFFSET + CONTENT_X_OFFSET + hbCursorPos,
                    y + CONTENT_HEIGHT);
        }
        
        cursorImage.draw(cursor1 - 8 , y + 16, 16, 16);
        cursorImage.draw(cursor2 - 8 , y + 16, 16, 16);
        cursorImage.draw(cursor3 - 8 , y + 16, 16, 16);
        cursorImage.draw(cursor4 - 8 , y + 16, 16, 16);
        
        String scoreText = "x " + scoreModifier;
        gr.setColor(Color.white);
        gr.setFont(unicodeFont);
        gr.drawString(scoreText, 
                120 + CONTENT_WIDTH, 
                Main.height - 55);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Level scrolling manager.
 * 
 * @author Anthony
 */
public class LevelManager {
    
    public static final int[] SEGMENT_IDS = {  12};
    public static final int SEGMENT_X_OFFSET = 48; //px 
    // TODO public static final int SEGMENTS_COUNT = nb images segments;
    
    private static final double BG_PARALLAX_MODIFIER = 0.1;
    private static final String BG_IMAGE = "sprites/map/Background.jpg";

    private LinkedList<LevelSegment> segmentsStack;
    private LinkedList<LevelSegment> bgStack;
    
    private Image lightning;
    
    // TODO black FX layer + functions (alpha)
    private Image blackFxImage;
    // TODO speed FX layer + functions (alpha)

    public LevelManager() throws SlickException {
        
        // Load 2 segments, because of offset
        segmentsStack = new LinkedList<LevelSegment>();
        segmentsStack.add(selectNextSegment());
        LevelSegment newSegment = selectNextSegment();
        newSegment.setCoords(new Point2D.Float((float)(Main.width - SEGMENT_X_OFFSET), 0f));
        segmentsStack.add(newSegment);
        
        bgStack = new LinkedList<LevelSegment>();
        bgStack.add(new LevelSegment(BG_IMAGE, false));
        
        lightning = Sprite.getImage("sprites/fx/Lightning.png");
        
        blackFxImage = Sprite.getImage("sprites/fx/BlackFX.png");
        blackFxImage.setAlpha(0.0f);
    }
 
    public void update(double deltaPixels) throws SlickException {

        if (deltaPixels <= 0) {
            return;
        }

        // -----
        // Update Segments stack
        if (!segmentsStack.isEmpty()) {
            LevelSegment headSegment = segmentsStack.peek();
            float headTopLeftX = (float) (headSegment.getCoords().getX());
            float headTopRightX = (float) (headSegment.getCoords().getX() + Main.width);
            
            if (segmentsStack.size() <= 2  && headTopRightX <= 2 * SEGMENT_X_OFFSET) {
                // Head segment is starting to go out of screen load another one
                LevelSegment newSegment = selectNextSegment();
                newSegment.setCoords(new Point2D.Float((float) (Main.width * segmentsStack.size() + headTopLeftX - 2* SEGMENT_X_OFFSET), 0));
                segmentsStack.add(newSegment);
                
            } else if (headTopLeftX <= -Main.width - SEGMENT_X_OFFSET) {
                // Head segment is out of screen, remove it
                segmentsStack.remove();
            }
        }

        // Update all segments position
        for (LevelSegment segment : segmentsStack) {
            segment.addToX(-deltaPixels);
        }
        // -----
        // Update Background stack
        if (!bgStack.isEmpty()) {
            LevelSegment headSegment = bgStack.peek();
            float headX = (float) (headSegment.getCoords().getX());

            if (headX <= 0 && bgStack.size() < 2) {
                // Head segment is starting to go out of screen load another one
                LevelSegment newSegment = new LevelSegment(BG_IMAGE, false);
                newSegment.setCoords(new Point2D.Float((float) (Main.width * bgStack.size() + headX), 0));
                bgStack.add(newSegment);
                
            } else if (headX <= -Main.width) {
                // Head segment is out of screen, remove it
                bgStack.remove();
            }
        }
        
        // Update all backgrounds position
        for (LevelSegment bg : bgStack) {
            bg.addToX((int) (-deltaPixels * BG_PARALLAX_MODIFIER));
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        // Render bg first
        for (LevelSegment bg : bgStack) {
            bg.getRenderable().draw((float) bg.getCoords().getX(), (float) bg.getCoords().getY());
        }
        
        for (LevelSegment segment : segmentsStack) {
            segment.getRenderable().draw((float) segment.getCoords().getX(), (float) segment.getCoords().getY());
        }
        
        // Render FX layers last
        if (blackFxImage.getAlpha() > 0) {
            blackFxImage.draw(0, 0);
        }
        
        lightning.draw(0, 0, Main.width, Main.height);
    }

    private LevelSegment selectNextSegment() throws SlickException {
        int segmentId = SEGMENT_IDS[(int) (Math.floor(Math.random() * SEGMENT_IDS.length))];
        
        // Flip desactivé
        boolean isFlippedHorizontally = false;//Math.random() > 0.5 ? true : false;     
        
        return new LevelSegment("sprites/map/MAP_" + segmentId + ".png", isFlippedHorizontally);
    }
    
    public void setBlackFxAlpha(float alpha) {
        blackFxImage.setAlpha(alpha);
    }
}

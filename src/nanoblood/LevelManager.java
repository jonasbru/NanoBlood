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
 * 
 * @author Anthony
 */
public class LevelManager {
    
    public static final int[] SEGMENT_IDS = { 1, 3 };
    // TODO public static final int SEGMENTS_COUNT = nb images segments;
    private static final double BG_PARALLAX_MODIFIER = 0.1;
    private static final String BG_IMAGE = "sprites/map/BG_Test.png";

    private LinkedList<LevelSegment> segmentsStack;
    private LinkedList<LevelSegment> bgStack;
    
    // TODO black FX layer + functions (alpha)
    private Image blackFxImage;
    // TODO speed FX layer + functions (alpha)

    public LevelManager() throws SlickException {
        segmentsStack = new LinkedList<LevelSegment>();
        segmentsStack.add(selectNextSegment());
        
        bgStack = new LinkedList<LevelSegment>();
        bgStack.add(new LevelSegment(BG_IMAGE, false));
        
        blackFxImage = Sprite.getImage("sprites/fx/BlackFx.png");
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
            float headX = (float) (headSegment.getCoords().getX());

            if (headX <= 0) {
                // Head segment is starting to go out of screen load another one
                LevelSegment newSegment = selectNextSegment();
                newSegment.setCoords(new Point2D.Float((float) (Main.width * segmentsStack.size() + headX), 0));
                segmentsStack.add(newSegment);
                
            } else if (headX <= -Main.width) {
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

            if (headX <= 0) {
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
        
        // Render map segments
        for (LevelSegment segment : segmentsStack) {
            segment.getRenderable().draw((float) segment.getCoords().getX(), (float) segment.getCoords().getY());
        }
        
        // Render FX layers last
        if (blackFxImage.getAlpha() > 0) {
            blackFxImage.draw(0, 0);
        }
    }

    private LevelSegment selectNextSegment() throws SlickException {
        // TODO choisir segment random mais différent au courant ?
        
        // WIP
        int segmentId = SEGMENT_IDS[(int) (Math.floor(Math.random() * SEGMENT_IDS.length))];
        boolean isFlippedHorizontally = Math.random() > 0.5 ? true : false;     
        return new LevelSegment("sprites/map/MAP_" + segmentId + ".png", isFlippedHorizontally);
    }
    
    public void setBlackFxAlpha(float alpha) {
        blackFxImage.setAlpha(alpha);
    }
}

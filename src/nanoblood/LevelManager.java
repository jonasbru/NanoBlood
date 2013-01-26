/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import java.util.ArrayList;
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

    private LinkedList<LevelSegment> segments;
    //private LinkedList<LevelSegment> bgStack;
    // TODO black FX layer + functions (alpha)
    // TODO speed FX layer + functions (alpha)

    public LevelManager() throws SlickException {
        segments = new LinkedList<LevelSegment>();
        segments.add(selectNextSegment()); // load 1st segment
    }

    public void update(int deltaPixels) throws SlickException {

        if (deltaPixels <= 0) {
            return;
        }

        if (!segments.isEmpty()) {
            LevelSegment headSegment = segments.peek();
            float headX = (float) (headSegment.getCoords().getX());

            if (headX <= 0) {
                // Head segment is starting to go out of screen load another one
                LevelSegment newSegment = selectNextSegment();
                newSegment.setCoords(new Point2D.Float((float) (Main.width * segments.size() + headX), 0));
                segments.add(newSegment);
                
            } else if (headX <= -Main.width) {
                // Head segment is out of screen, remove it
                segments.remove();
            }
        }

        // Update all segments position
        for (LevelSegment segment : segments) {
            segment.addToX(-deltaPixels);
        }
        
        // TODO update bg pos
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        for (LevelSegment segment : segments) {
            segment.getRenderable().draw((float) segment.getCoords().getX(), (float) segment.getCoords().getY());
        }
    }

    private LevelSegment selectNextSegment() throws SlickException {
        // TODO choisir segment random mais différent au courant ?
        
        // WIP
        int segmentId = SEGMENT_IDS[(int) (Math.floor(Math.random() * SEGMENT_IDS.length))];
        
        boolean isFlippedHorizontally = Math.random() > 0.5 ? true : false;
        
        return new LevelSegment(segmentId, isFlippedHorizontally);

    }
}

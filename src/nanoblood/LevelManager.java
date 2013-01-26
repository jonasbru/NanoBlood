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

    private LinkedList<LevelSegment> segments;

    public LevelManager() throws SlickException {
        segments = new LinkedList<LevelSegment>();
        segments.add(selectNextSegment()); // load 1st segment
    }
 
    public void update(double deltaPixels) throws SlickException {

        if (deltaPixels <= 0) {
            return;
        }

        if (!segments.isEmpty()) {
            if (segments.peek().getCoords().getX() <= 0) {
                LevelSegment newSegment = selectNextSegment();
                newSegment.setCoords(new Point2D.Float((float) (Main.width * segments.size() + segments.peek().getCoords().getX()), 0));
                segments.add(newSegment);
                
                /*
                newSegment = selectNextSegment();
                newSegment.setCoords(new Point2D.Float(Main.width * 2, 0));
                segments.add(newSegment);
                */
            }
            else if (segments.peek().getCoords().getX() <= -Main.width) { // segment out of screen
                 segments.remove();
            }
        }

        for (LevelSegment segment : segments) {
            segment.addToX(-deltaPixels);
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {
        ArrayList<Image> layers;
        for (LevelSegment segment : segments) {
            layers = segment.getLayers();
            for (Image img : layers) {
                img.draw((float) segment.getCoords().getX(), (float) segment.getCoords().getY());
            }
        }
    }

    private LevelSegment selectNextSegment() throws SlickException {
        // TODO choisir segment suivant random, mais différent au courant
        // TODO appliquer symétrie random
        int segmentId = Math.random() > 0.5 ? 1 : 3;
        return new LevelSegment(segmentId); // WIP

    }

    public void increaseScrollSpeed() {
        // à appeler avec l'appui de la touche espace
    }
}

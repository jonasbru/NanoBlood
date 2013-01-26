/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Anthony
 */
public class LevelSegment {
    
    public static int LAYERS_COUNT = 1;//3;
    
    protected Point2D coords;
    protected ArrayList<Image> layers;
    
    public LevelSegment(int segmentId) throws SlickException {
        coords = new Point2D.Float(0, 0);
        layers = new ArrayList<Image>(LAYERS_COUNT);
        
        // BG
        //layers.add(Sprite.getImage("sprites/map/bg" + segmentId + ".png"));
        // Vein
        layers.add(Sprite.getImage("sprites/map/MAP_" + segmentId + ".png"));
        // Obstacles
        //layers.add(Sprite.getImage("sprites/map/obstacle" + segmentId + ".png"));
    }
    
    public ArrayList<Image> getLayers() {
        return layers;
    }

    public Point2D getCoords() {
        return coords;
    }

    public void setCoords(Point2D coords) {
        this.coords = coords;
    }
    
    public void addToX(int dx) {
        this.coords.setLocation(coords.getX() + dx, coords.getY());
    }
}

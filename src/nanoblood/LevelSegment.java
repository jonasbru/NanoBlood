/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Anthony
 */
public class LevelSegment extends Sprite {
    
    protected Image segmentImage;
    private boolean isHorizontallyFlipped = false;
    
    public LevelSegment(int segmentId, boolean isFlipped) throws SlickException {
        
        this.coords = new Point2D.Double(0, 0);
        this.isHorizontallyFlipped = isFlipped;
        this.segmentImage = Sprite.getImage("sprites/map/MAP_" + segmentId + ".png");
    }

    @Override
    public Renderable getRenderable() {
        if (this.isHorizontallyFlipped) {
            return this.segmentImage.getFlippedCopy(true, false);
        }
        else {
            return segmentImage;
        }
    }
    
    public void addToX(int dx) {
        this.coords.setLocation(coords.getX() + dx, coords.getY());
    }
}

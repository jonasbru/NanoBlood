package nanoblood;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

/**
 * Both for level segments and background segments.
 *
 * @author jammers
 */
public class LevelSegment extends Sprite {

    protected Image segmentImage;
    private boolean isHorizontallyFlipped = false;
    private CollisionsCollection cc;

    public LevelSegment(String imagePath, boolean isFlipped, World w) throws SlickException, FileNotFoundException {

        this.coords = new Point2D.Double(0, 0);
        this.isHorizontallyFlipped = isFlipped;
        this.segmentImage = Sprite.getImage(imagePath);
        if (null != w) {// if null is passed, then it means we are not concerned about collisions
            this.cc = CollisionsCollection.fromFile(imagePath.replace(".png", ".map"), 10.0f);
        }
    }

    public CollisionsCollection getCC() {
        return this.cc;
    }

    @Override
    public Renderable getRenderable() {
        if (this.isHorizontallyFlipped) {
            return this.segmentImage.getFlippedCopy(true, false);
        } else {
            return segmentImage;
        }
    }

    public void addToX(double dx) {
        this.coords.setLocation(coords.getX() + dx, coords.getY());
    }

    public void goodBye(World w) {
        this.cc.removeFromWorld(w);
    }

    void draw(Graphics gr) {
        this.getRenderable().draw((float) this.getCoords().getX(), (float) this.getCoords().getY());
        if (null !=  this.cc) {
            this.cc.draw(gr);
        }
    }
}

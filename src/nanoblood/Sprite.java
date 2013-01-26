/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.TreeMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

/**
 *
 * @author jonas
 */
public abstract class Sprite {

    private static Map<String, Image> sprites = new TreeMap<String, Image>();

    public static Image getImage(String path) throws SlickException {
         if(sprites.containsKey(path)){
            return sprites.get(path);
        } else {
            Image i = new Image(path);
            sprites.put(path, i);
            return i;
        }
    }

    public abstract Renderable getRenderable();

    protected Point2D coords= new Point2D.Double();

    public Point2D getCoords() {
        return coords;
    }

    public void setCoords(Point2D coords) {
        this.coords = coords;
    }
}

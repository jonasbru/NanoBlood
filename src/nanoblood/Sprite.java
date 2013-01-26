/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.Arrays;
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
        if (sprites.containsKey(path)) {
            return sprites.get(path);
        } else {
            Image i = new Image(path);
            sprites.put(path, i);
            return i;
        }
    }

    public static String intToString(int num, int digits) {
        assert digits > 0 : "Invalid number of digits";

        // create variable length array of zeros
        char[] zeros = new char[digits];
        Arrays.fill(zeros, '0');
        // format number as String
        DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

        return df.format(num);
    }

    //--------------------------- END STATIC ---------------------------

    public abstract Renderable getRenderable();
    
    protected Point2D coords = new Point2D.Double();

    public Point2D getCoords() {
        return coords;
    }

    public void setCoords(Point2D coords) {
        this.coords = coords;
    }

    public void setCoords(int x, int y) {
        this.coords.setLocation(x, y);
    }
}


package nanoblood;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jammers
 */
public class CollisionsCollection {
    List<Point2D> ptsList;
    int radius;
    public static CollisionsCollection fromFile(String path, int radius) throws FileNotFoundException {
        List<Point2D> ptsList = new ArrayList<Point2D>(100);
        Scanner sc = new Scanner(new File(path));
        while(sc.hasNext()) {
            Point2D a = new Point2D.Double() {};
            ptsList.add(a);
        }
        return new CollisionsCollection(ptsList, radius);
    }

    public CollisionsCollection(List<Point2D> ptsList, int radius) {
        this.ptsList = ptsList;
        this.radius = radius;
    }
    
}

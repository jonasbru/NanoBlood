
package nanoblood;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author jammers
 */
public class CollisionsCollection {
    List<Point2D> ptsList;
    Body body;
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
    
    public void injectIntoWorld(World w, float x, float y) {
        BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		body = w.createBody(bdef);
        
		for(Point2D p : ptsList) {
			CircleShape shape = new CircleShape();
			shape.m_type = ShapeType.CIRCLE;
			shape.m_radius = this.radius;
			shape.m_p.x = (float) p.getX();
			shape.m_p.y = (float) p.getY();
			body.createFixture(shape, 1.0f);//density=1.0
		}
    }
    
}

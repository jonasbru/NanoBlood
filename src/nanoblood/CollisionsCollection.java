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
    float radius;

    public static CollisionsCollection fromFile(String path, float radius) throws FileNotFoundException {
        List<Point2D> ptsList = new ArrayList<Point2D>(100);
        Scanner sc = new Scanner(new File(path));
        while (sc.hasNext()) {
            Point2D a = new Point2D.Double() {
            };
            ptsList.add(a);
        }
        return new CollisionsCollection(ptsList, radius);
    }

    public CollisionsCollection(List<Point2D> ptsList, float radius) {
        this.ptsList = ptsList;
        this.radius = radius;
    }

    public void injectIntoWorld(World w, float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        body = w.createBody(bdef);

        for (int i = 0; i < ptsList.size() - 1; i++) {
            Point2D p = ptsList.get(i);
            Point2D p2 = ptsList.get(i + 1);
            int n = (int) (Point2D.distance(p.getX(), p.getY(), p2.getX(), p2.getY()) / radius);
            int dx = (int) (p.getX() / (double) n);
            int dy = (int) (p.getY() / (double) n);
            // There's room for another one in between
            for (int j = 0; j < n; j++) {// Create circles to fill in the blanks as long as there is room for them
                CircleShape shape = new CircleShape();
                shape.m_type = ShapeType.CIRCLE;
                shape.m_radius = this.radius;
                shape.m_p.x = (float) (p.getX() + dx);
                shape.m_p.y = (float) (p.getY() + dy);
                body.createFixture(shape, 1.0f);//density=1.0
            }
            CircleShape shape = new CircleShape();
            shape.m_type = ShapeType.CIRCLE;
            shape.m_radius = this.radius;
            shape.m_p.x = (float) p.getX();
            shape.m_p.y = (float) p.getY();
            body.createFixture(shape, 1.0f);//density=1.0
        }
    }
}

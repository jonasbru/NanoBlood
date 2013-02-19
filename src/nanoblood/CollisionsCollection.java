package nanoblood;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author jammers
 */
public class CollisionsCollection {
    private static final Color[] colors = { Color.black, Color.blue, Color.yellow, Color.red, Color.cyan, Color.darkGray, Color.gray, Color.magenta, Color.pink};
    private static int counter = 0;

    List<Point2D> ptsList;
    Body body;
    float radius;

    public static CollisionsCollection fromFile(String path, float radius) {
        try {
            List<Point2D> ptsList = new ArrayList<Point2D>(100);
            Scanner sc = new Scanner(new File(path));
            sc.useLocale(Locale.US);
            while (sc.hasNext()) {
                Point2D a = new Point2D.Double();
                double y = sc.nextDouble() * Main.height;
                double x = sc.nextDouble() * Main.width;
                a.setLocation(x, y);
                ptsList.add(a);
                if (sc.hasNext()) {// check in case there is no blank last line
                    sc.nextLine();
                }
            }
            return new CollisionsCollection(ptsList, radius, path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CollisionsCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private final String path;
    private int baseX;
    private Color color;

    public CollisionsCollection(List<Point2D> ptsList, float radius, String path) {
        this.ptsList = ptsList;
        this.radius = radius;
        this.path = path;
        this.color = CollisionsCollection.colors[CollisionsCollection.counter++ % CollisionsCollection.colors.length];
    }

    public void injectIntoWorld(World w, float scroll) {
        this.baseX = LevelManager.getForegroundLoadingCounter() * Main.width;

        for (int i = 0; i < ptsList.size() - 1; i++) {
            Point2D p = ptsList.get(i);
            inject(w, p, baseX);
        }
        inject(w, ptsList.get(ptsList.size() - 1), baseX);
//               BodyDef gndbd = new BodyDef();
//        gndbd.type = BodyType.STATIC;
//        gndbd.position.x = baseX + 40;
//        gndbd.position.y = 0;
//        gndbd.active = true;
//        Body gndb = w.createBody(gndbd);
//        int y = 0;
//        for (int i = 0; i < 800; i++) {
//            y += 30;
//            CircleShape cs = new CircleShape();
//            cs.m_p.x = baseX + 40;
//            cs.m_p.y = y;
//            cs.m_radius = 10.0f;
//            gndb.createFixture(cs, 1.0f);
//        }
    }

    public void removeFromWorld(World w) {
        w.destroyBody(body);
    }

    /**
     *
     * @param p point to insert
     * @param x
     */
    private void inject(World w, Point2D p, float baseX) {
        BodyDef bdef = new BodyDef();

        float x = GamePlay.px2m(p.getX() + baseX);
        float y = GamePlay.px2m(GamePlay.ySlick2Physics((float) p.getY()));
        bdef.position.x = x;
        bdef.position.y = y;
        bdef.type = BodyType.STATIC;
        bdef.active = true;
        body = w.createBody(bdef);

        CircleShape shape = new CircleShape();
        shape.m_type = ShapeType.CIRCLE;
        shape.m_radius = 4.0f;

//        FixtureDef fd = new FixtureDef();
//        fd.density = 1.0f;
//        fd.friction = 1.5f;
//        fd.shape = shape;
//        body.createFixture(fd);
        body.createFixture(shape, 1.0f);//density=1.0
//        System.out.println("Injecting circle at point=" + p + "\t" + shape.m_p);
    }

    void draw(Graphics gr) {
//        System.out.println("\n\n\n\n\n---------------------" + this.path + "-----------------------\n\n");
        for (Point2D p : ptsList) {
//            System.out.println(p);
            Color backup = gr.getColor();
            gr.setColor(this.color);
            gr.drawOval((float) p.getX() + baseX - GamePlay.getGP().scrolledDistance, (float) p.getY(), GamePlay.m2px(4.0f), GamePlay.m2px(4.0f));
            gr.setColor(backup);
        }
//        System.out.println("\n\n\n\n\n*******************************************************************\n\n");
    }
}

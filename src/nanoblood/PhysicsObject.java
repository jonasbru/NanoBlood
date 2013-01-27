/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.geom.Point2D;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author troll
 */
public class PhysicsObject {
    private static float scrolledDistance = 0.0f;

    static void setScrolledDistance(float scrolledDistance) {
        PhysicsObject.scrolledDistance = scrolledDistance;
    }
    private final Sprite sprite;
    private final Body body;
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public Body getBody() {
        return body;
    }
    
    public Point2D getPhyCoords() {
        Point2D tmp = new Point2D.Double() {};
        tmp.setLocation(body.getPosition().x, body.getPosition().x);
        return tmp;
    }
    
    public Point2D getCoords() {
        Point2D tmp = new Point2D.Double();
        Vec2 pos = body.getPosition();
        tmp.setLocation(GamePlay.m2px(pos.x - scrolledDistance), GamePlay.yFromPhysicsToSlick(GamePlay.m2px(pos.y)));
        return tmp;
    }
    
    public Vec2 getPhyCoordsVec() {
        return body.getPosition();
    }
    
    public void move(Vec2 v) {
        body.setLinearVelocity(v);
//        body.applyForce(v, body.getPosition());
    }
    
    public void move(float x, float y) {
        move(new Vec2(x, y));
    }
            
    PhysicsObject(Sprite s, Body b) {
        this.body = b;
        this.sprite = s;
    }
    
    public Renderable getRenderable() {
        Vec2 pos = body.getPosition();
        sprite.boundingBox.setX(GamePlay.m2px(pos.x));
        sprite.boundingBox.setY(GamePlay.yFromPhysicsToSlick(GamePlay.m2px(pos.y)));
        return sprite.getRenderable();
    }
    
    public void colideWithPlayer() {
        sprite.colideWithPlayer();
    }
    
    public void colide(Sprite s){
        sprite.colide(s);
    }
    
    static public PhysicsObject createFromCircSprite(Sprite s, World w) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.DYNAMIC;
        bdef.position.x = GamePlay.px2m(s.getCoords().getX());
        bdef.position.y = GamePlay.px2m((int) GamePlay.ySlick2Physics((float)s.getCoords().getY()));
        Body b = w.createBody(bdef);
        CircleShape shape = new CircleShape();
        shape.m_radius = GamePlay.px2m(s.boundingBox.getHeight());// circle => widht == height
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        return new PhysicsObject(s, b);
    }
    
    static public PhysicsObject createFromRectSprite(Sprite s, World w) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.DYNAMIC;
        bdef.position.x = GamePlay.px2m(s.getCoords().getX());
        bdef.position.y = GamePlay.px2m((int) GamePlay.ySlick2Physics((float)s.getCoords().getY()));
        Body b = w.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GamePlay.px2m(s.boundingBox.getWidth()), GamePlay.px2m(s.boundingBox.getWidth()));
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        b.createFixture(fd);
        return new PhysicsObject(s, b);
    }

    void moveImpulse(Vec2 v) {
        body.applyLinearImpulse(v, body.getPosition());
    }
}

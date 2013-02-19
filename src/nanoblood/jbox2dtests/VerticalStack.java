/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.jbox2dtests;

import nanoblood.CollisionsCollection;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class VerticalStack extends TestbedTest {

	private static final long BULLET_TAG = 1;
	public static final int e_columnCount = 5;
	public static final int e_rowCount = 16;
	Body m_bullet;
	private BodyDef playerBodyDef;
	private Body playerBody;

	@Override
	public Long getTag(Body argBody) {
		if (argBody == m_bullet) {
			return BULLET_TAG;
		}
		return super.getTag(argBody);
	}

	@Override
	public void processBody(Body argBody, Long argTag) {
		if (argTag == BULLET_TAG) {
			m_bullet = argBody;
			return;
		}
		super.processBody(argBody, argTag);
	}

	@Override
	public boolean isSaveLoadEnabled() {
		return true;
	}

	@Override
	public void initTest(boolean deserialized) {
		if (deserialized) {
			return;
		}
        
        
        CollisionsCollection cc = CollisionsCollection.fromFile("/home/troll/Dropbox/NanoBlood/sprites/map/MAP_1.map", 10.0f);
        cc.injectIntoWorld(getWorld(), 0.0f);
        
//				Vec2 gravity = new Vec2(0.0f, 100.0f);
//		World world = new World(gravity, true);

//		BodyDef gndBodydef = new BodyDef();
//		gndBodydef.position.set(0.0f, -10.0f);
//		Body gndBody = getWorld().createBody(gndBodydef);
//		PolygonShape gndBox = new PolygonShape();
//		gndBox.setAsBox(300.0f, 10.0f);
//		gndBody.createFixture(gndBox, 0.0f);
//
		playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DYNAMIC;
		playerBodyDef.position.x = 20.0f;
		playerBodyDef.position.y = 23.0f;
		playerBody = getWorld().createBody(playerBodyDef);
//		playerBodyDef.fixedRotation = true;
//		playerBodyDef.angularDamping = 100000f;
//		playerBodyDef.angularVelocity = 1000.0f;
		playerBodyDef.active = true;
//		playerBody.applyTorque(100.0f);
//		playerBody.setAngularVelocity(24);
		playerBody.setAngularDamping(200);
		CircleShape playerShape = new CircleShape();
//		PolygonShape playerShape = new PolygonShape();
//		playerShape.setAsBox(5.0f, 5.0f);
		playerShape.m_type = ShapeType.CIRCLE;
		playerShape.m_radius = 5.0f;
		
		FixtureDef playerFD = new FixtureDef();
		playerFD.shape = playerShape;
		playerFD.density = 1.0f;
		playerFD.friction = 2.5f;
		playerBody.createFixture(playerFD);

//		Vec2 center = new Vec2(0.0f, 0.0f);
//		for (int i = 0; i < 50; i++) {
//			CircleShape shape = new CircleShape();
//			shape.m_type = ShapeType.CIRCLE;
//			shape.m_radius = 2.0f;
//			shape.m_p.x = center.x;
//			shape.m_p.y = center.y;
//			gndBody.createFixture(shape, 1.0f);
//			center = new Vec2(center.x + 2.0f, center.y + 2.0f);
//		}
//        
//        	CircleShape shape = new CircleShape();
//			shape.m_type = ShapeType.CIRCLE;
//			shape.m_radius = 2.0f;
//			shape.m_p.x = center.x;
//			shape.m_p.y = center.y;
//			playerBody.createFixture(shape, 1.0f);
//
//		float timeStep = 1.0f / 60.0f;
//		int velocityIterations = 6;
//		int positionIterations = 2;

        
        
        
		m_bullet = null;
	}

	@Override
	public void keyPressed(char argKeyChar, int argKeyCode) {
//		playerBody.applyLinearImpulse(new Vec2(0.0f, 10000.0f), playerBody.getPosition());
//		playerBody.setLinearDamping(1.1f);
//        playerBody.setTransform(new Vec2(0.0f, 40.0f), 0.0f);
		System.out.println("POUET");
	}

	@Override
	public void step(TestbedSettings settings) {
		super.step(settings);
//				System.out.println("Pouet");
//		playerBodyDef.angularVelocity = 1000.0f;
//		playerBodyDef.active = true;
//		System.out.println("The player is currently at: " + playerBody.getPosition() + " // " + playerBody.getAngle());
		addTextLine("Press ',' to launch bullet.");
	}

	@Override
	public String getTestName() {
		return "Vertical Stack";
	}
}
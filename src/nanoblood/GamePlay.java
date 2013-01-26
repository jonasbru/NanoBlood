/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.util.ArrayList;
import java.util.List;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author jonas
 */
public class GamePlay extends BasicGameState {

	int stateID = -1;
	Player player;
	LevelManager levelManager;
	List<StaticObject> objects;
	float bloodSpeed = 0;
	final int bloodSpeedImpulse = 3;
	final double bloodSpeedDecrease = 0.01;
	private Vec2 gravity;
	private BodyDef gndBodydef;
	private Body gndBody;
	private PolygonShape gndBox;
	private BodyDef playerBodyDef;
	private Body playerBody;
	private PolygonShape playerShape;
	private FixtureDef playerFD;
	private float timeStep;
	private int velocityIterations;
	private int positionIterations;
	private World world;
	int totalDistance = 0;
	int nextDistancePopObstacle;
	int deltaDistancePopObstacle = 200;

	GamePlay(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.player = new Player();
		this.levelManager = new LevelManager();
		this.objects = new ArrayList<StaticObject>();

		nextDistancePopObstacle = Main.width + deltaDistancePopObstacle;

		player.setCoords(200, Main.height / 2);

		for (int i = 0; i < 6; i++) {
			Obstacle o = Obstacle.getRandomObstacle();
			o.setCoords(i * 200, (int) (Math.random() * Main.height));
			this.objects.add(o);
		}

		initPhysics();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
		this.levelManager.render(gc, sbg, grphcs);

		for (StaticObject so : this.objects) {
			so.getRenderable().draw((float) so.coords.getX(), (float) so.coords.getY());
		}

		this.player.getRenderable().draw((float) this.player.getCoords().getX(), (float) this.player.getCoords().getY());
		this.player.getCanons().draw((float) this.player.getCoords().getX(), (float) this.player.getCoords().getY() - 4);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		removeObjects();
		addObjects();

		manageInput(gc, sbg, i);

		updatePhysics();
		updateObjects();

		this.levelManager.update(this.bloodSpeed);

		manageColisions();
	}

	private void manageInput(GameContainer gc, StateBasedGame sbg, int delta) {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_UP)) {
			player.goUp();
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			player.goDown();
		} else {
			player.stop();
		}

		if (input.isKeyPressed(Input.KEY_SPACE)) {
			this.bloodSpeed += this.bloodSpeedImpulse;
		} else {
			this.bloodSpeed -= this.bloodSpeedDecrease * this.bloodSpeed;
			if (this.bloodSpeed < 0) {
				this.bloodSpeed = 0;
			}
		}

		totalDistance += bloodSpeed;
	}

	private void updateObjects() {
		List<StaticObject> toRemove = new ArrayList<StaticObject>();

		for (StaticObject so : this.objects) {
			so.move((int) -this.bloodSpeed / 3, 0);
			if (so.coords.getX() < -50) {
				toRemove.add(so);
			}
		}

		for (StaticObject so : toRemove) {
			this.objects.remove(so);
		}

		// WIP : FX
//        if (bloodSpeed < 100) {
//            float alpha = 1 * (100 - bloodSpeed) / (100);
//            System.out.println(bloodSpeed + " -> alpha = " + alpha);
//            levelManager.setBlackFxAlpha(alpha);
//        }
	}

	private void manageColisions() {

		for (StaticObject so : this.objects) {
			if (this.player.boundingBox.intersects(so.getBoundingBox())) {
				so.colideWithPlayer();
			}
		}
	}

	private void removeObjects() {
		for (int i = 0; i < this.objects.size();) {
			if (this.objects.get(i).needToRemove()) {
				this.objects.remove(i);
			} else {
				i++;
			}
		}
	}

	private void addObjects() throws SlickException {
		if (totalDistance > nextDistancePopObstacle) {
			Obstacle o = Obstacle.getRandomObstacle();
			o.setCoords(Main.width + 300 - (totalDistance - nextDistancePopObstacle), (int) (Math.random() * Main.height));
			this.objects.add(o);
			nextDistancePopObstacle += deltaDistancePopObstacle;
		}
	}

	private void initPhysics() {
		gravity = new Vec2(100.0f, 0);
		world = new World(gravity, true);
		gndBodydef = new BodyDef();
		gndBodydef.position.set(0.0f, (float) (0.2 * Main.width));
		gndBody = world.createBody(gndBodydef);
		gndBox = new PolygonShape();
		gndBox.setAsBox(10.0f, (float) Main.width);
		gndBody.createFixture(gndBox, 0.0f);
		playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DYNAMIC;
		playerBodyDef.position.x = (float) Main.width / 2;
		playerBodyDef.position.y = (float) this.player.getCoords().getY();
		playerBody = world.createBody(playerBodyDef);
		playerShape = new PolygonShape();
		playerShape.setAsBox(this.player.getWidth() / 2, this.player.getHeight() / 2);
		playerFD = new FixtureDef();
		playerFD.shape = playerShape;
		playerFD.density = 1.0f;
		//* The two next lines together allow us to have friction against a number of circles even if we are a circle ourself
		playerFD.friction = 1.5f;
		playerBody.setAngularDamping(200);
		playerBody.createFixture(playerFD);
		timeStep = 1.0f / 60.0f;
		velocityIterations = 6;
		positionIterations = 2;
		for (int i = 0; i < 50; i++) {
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(10.0f, 10.0f);
			gndBody.createFixture(shape, 1.0f);
		}
	}

	private void updatePhysics() {
		world.step(timeStep, velocityIterations, positionIterations);
		this.player.setY(playerBody.getPosition().y);
		this.player.setX(playerBody.getPosition().x);

	}
}

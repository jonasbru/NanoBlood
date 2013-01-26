/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import java.awt.Point;
import java.util.Date;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author jonas
 */
public class Player extends Sprite {

    private final Body body;
    private Vec2 topImpulseVec = new Vec2(0.0f, 5000.0f);
    private Vec2 downImpulseVec = new Vec2(0.0f, -5000.0f);
    protected static final float INIT_X = 43;
    protected static final float INIT_Y = 13;
    protected static final float WIDTH = 42;
    protected static final float HEIGHT = 43;

    private enum Anim {

        STATIC, UP_GO, UP_BACK, DOWN_GO, DOWN_BACK
    }
    Anim currentAnim;
    Image staticShip;
    Animation downGo;
    Animation downBack;
    Animation upGo;
    Animation upBack;
    Animation shield;
    boolean shieldActivated = false;
    Date lastShieldActivation = null;
    int shieldSeconds = 5;
    Image canons;
    final int VERTICAL_SPEED = 8;

    public int getWidth() {
        return this.staticShip.getWidth();
    }

    public int getHeight() {
        return this.staticShip.getHeight();
    }

    public Player(Body body) throws SlickException {
        this.body = body;
        this.staticShip = Sprite.getImage("sprites/player/static.png");
        this.canons = Sprite.getImage("sprites/player/canons.png");
        this.canons.rotate(90);

        this.boundingBox = new Rectangle(INIT_X, INIT_Y, WIDTH, HEIGHT);

        Image anim[] = new Image[5];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/player/goYUp/mainperso_mouvementx-_" + Sprite.intToString(i, 5) + ".png");
        }
        this.upGo = new Animation(anim, 50, true);
        this.upGo.setLooping(false);

        anim = new Image[5];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/player/backYUp/mainperso_mouvementx-_" + Sprite.intToString(i, 5) + ".png");
        }
        this.upBack = new Animation(anim, 50, true);
        this.upBack.setLooping(false);

        anim = new Image[5];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/player/goYDown/mainperso_mouvementx+_" + Sprite.intToString(i, 5) + ".png");
        }
        this.downGo = new Animation(anim, 50, true);
        this.downGo.setLooping(false);

        anim = new Image[5];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/player/backYDown/mainperso_mouvementx+_" + Sprite.intToString(i, 5) + ".png");
        }
        this.downBack = new Animation(anim, 50, true);
        this.downBack.setLooping(false);

        anim = new Image[20];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/obstacles/BOUCLIER/Shield" + Sprite.intToString(i, 5) + ".png");
        }
        this.shield = new Animation(anim, 50, true);
        this.shield.start();


        currentAnim = Anim.STATIC;
    }

    @Override
    public Renderable getRenderable() {
        //* Updating graphics
        Vec2 pos = body.getPosition();
        float x = GamePlay.m2px(pos.x) % Main.width;
        float y = GamePlay.yFromPhysicsToSlick(GamePlay.m2px(pos.y));
        this.coords.setLocation(x, y);
        this.boundingBox.setY(y);
        this.boundingBox.setX(x);
        //* Sending actual renderable
        switch (this.currentAnim) {
            case UP_GO:
                return this.upGo;

            case UP_BACK:
                return this.upBack;

            case DOWN_GO:
                return this.downGo;

            case DOWN_BACK:
                return this.downBack;
        }

        return this.staticShip;
    }

    public void goUp() {
        body.applyLinearImpulse(topImpulseVec, body.getPosition());

        if (currentAnim != Anim.UP_GO && this.upGo.isStopped()) {
            this.upBack.stop();
            this.downGo.stop();
            this.downBack.stop();

            this.upGo.restart();

            currentAnim = Anim.UP_GO;
        }
    }

    public void goDown() {
        body.applyLinearImpulse(downImpulseVec, body.getPosition());

        if (currentAnim != Anim.DOWN_GO && this.downGo.isStopped()) {
            this.upGo.stop();
            this.upBack.stop();
            this.downBack.stop();

            this.downGo.restart();

            currentAnim = Anim.DOWN_GO;
        }
    }

    public void stop() {
        this.upGo.stop();
        this.downGo.stop();

        if (currentAnim == Anim.UP_GO) {
            currentAnim = Anim.UP_BACK;
            this.upBack.restart();
        } else if (currentAnim == Anim.DOWN_GO) {
            currentAnim = Anim.DOWN_BACK;
            this.downBack.restart();
        }


//        this.staticA.start();
//        this.down.stop();
//        this.up.stop();
//
//        currentAnim = Anim.STATIC;
    }

    public void activateShield(boolean active) {
        this.shieldActivated = active;
        if (active) {
            lastShieldActivation = new Date();
        }
    }

    public boolean isShieldActivated() {
        if (shieldActivated) {
            Date d = new Date();
            float r = shieldSeconds * 1000 - (d.getTime() - lastShieldActivation.getTime());

            if (r <= 0) {
                shieldActivated = false;
            }
        }

        return this.shieldActivated;
    }

    public Image getCanons() {
        return this.canons;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

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

    private enum Anim {

        STATIC, UP_GO, UP_BACK, DOWN_GO, DOWN_BACK
    }
    Anim currentAnim;
    Image staticShip;
    Animation downGo;
    Animation downBack;
    Animation upGo;
    Animation upBack;
    final int VERTICAL_SPEED = 8;

    public Player() throws SlickException {
        this.staticShip = Sprite.getImage("sprites/player/static.png");

        this.boundingBox = new Rectangle(43, 13, 42, 43);

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

        currentAnim = Anim.STATIC;
    }

    @Override
    public Renderable getRenderable() {
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
        this.coords.setLocation(this.coords.getX(), this.coords.getY() - VERTICAL_SPEED);
        this.boundingBox.setY(boundingBox.getY() - VERTICAL_SPEED);

        if (currentAnim != Anim.UP_GO && this.upGo.isStopped()) {
            this.upBack.stop();
            this.downGo.stop();
            this.downBack.stop();

            this.upGo.restart();

            currentAnim = Anim.UP_GO;
        }
    }

    public void goDown() {
        this.coords.setLocation(this.coords.getX(), this.coords.getY() + VERTICAL_SPEED);
        this.boundingBox.setY(boundingBox.getY() + VERTICAL_SPEED);

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
}

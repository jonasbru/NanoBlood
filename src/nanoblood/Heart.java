/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import nanoblood.sound.SoundID;
import nanoblood.sound.SoundManager;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 *
 * @author jonas
 */
public class Heart extends Obstacle{
private enum Anim {

        STATIC
    }
    Anim currentAnim;
    Animation staticA;

    public Heart() throws SlickException {

        this.boundingBox = new Circle((int) this.coords.getX() + 50, (int) this.coords.getY() + 50, 35);

        Image anim[] = new Image[20];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/obstacles/Coeur/COEUR_" + Sprite.intToString(i, 5) + ".png");
        }
        this.staticA = new Animation(anim, 100, true);

        this.currentAnim = Anim.STATIC;
    }

    @Override
    public Renderable getRenderable() {
        switch (this.currentAnim) {
            case STATIC:
                return this.staticA;
        }

        return this.staticA;
    }

    @Override
    public void colideWithPlayer() {
        this.remove = true;

        GamePlay.getGP().addLife(20);
        SoundManager.INSTANCE.playAsSoundEffect(SoundID.LIFEUP, false);
    }
}

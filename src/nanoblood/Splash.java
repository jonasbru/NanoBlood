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
public class Splash extends Obstacle {

    private enum Anim {

        STATIC
    }
    Anim currentAnim;
    Animation staticA;

    public Splash() throws SlickException {

        this.boundingBox = new Circle((int) this.coords.getX() + 50, (int) this.coords.getY() + 50, 1);

        Image anim[] = new Image[20];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = Sprite.getImage("sprites/splashseq/splashbrut" + Sprite.intToString(i, 4) + ".png");
        }
        this.staticA = new Animation(anim, 30, true);
        staticA.setLooping(false);
        
        // Random splash sound
        int splashID = (int) Math.floor(Math.random() * 3);
        switch(splashID) {
            default:
            case 0:
                SoundManager.INSTANCE.playAsSoundEffect(SoundID.SPLASH1, 1f, 0.1f, false);
                break;
                
            case 1:
                SoundManager.INSTANCE.playAsSoundEffect(SoundID.SPLASH2, 1f, 0.1f, false);
                break;
                
            case 2:
                SoundManager.INSTANCE.playAsSoundEffect(SoundID.SPLASH3, 1f, 0.1f, false);
                break;
        }
        

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
    }
}

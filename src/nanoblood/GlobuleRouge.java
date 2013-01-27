/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 *
 * @author jonas
 */
public class GlobuleRouge extends Obstacle {

    private enum Anim {

        STATIC
    }
    Anim currentAnim;
    Animation staticA;

    public GlobuleRouge() throws SlickException {

        this.boundingBox = new Circle((int) this.coords.getX() + 50, (int) this.coords.getY() + 50, 10);

        int rand = (int) (Math.random() * 2);

        Image anim[] = new Image[20];
        for (int i = 0; i < anim.length; i++) {
            if (rand < 1) {
                anim[i] = Sprite.getImage("sprites/obstacles/GLOBUL_ROUGE/Glob-rouge1_" + Sprite.intToString(i, 5) + ".png");
            } else if (rand < 2) {
                anim[i] = Sprite.getImage("sprites/obstacles/GLOBUL_ROUGE_2/Glob-rouge2_" + Sprite.intToString(i, 5) + ".png");
            }
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
        
        if (!GamePlay.getGP().player.isShieldActivated()) {
            GamePlay.getGP().life -= 10;

            GamePlay.getGP().setChanged();
            GamePlay.getGP().notifyObserver(GamePlay.getGP().lifeDisplay);
        }
    }
}

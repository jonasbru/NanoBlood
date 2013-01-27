/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;

/**
 *
 * @author jonas
 */
public abstract class Obstacle extends StaticObject {

    public static Obstacle getRandomObstacle() throws SlickException {
        int rand = (int) (Math.random() * 30);
        if(rand < 1) {
            return new Cancer();
        }
        if(rand < 2) {
            return new Virus();
        }
        if(rand < 3) {
            return new GlobuleBlanc();
        }
        if(rand < 4) {
            return new Heart();
        }
        if(rand < 5) {
            return new Bouclier();
        }
        if(rand < 30) {
            return new GlobuleRouge();
        }

        return null;
    }

    @Override
    public void colideWithPlayer() {
        this.remove = true;
    }

    public void die() {
        this.remove = true;

        Splash s = null;
        try {
            s = new Splash();
        } catch (SlickException ex) {
            Logger.getLogger(Cancer.class.getName()).log(Level.SEVERE, null, ex);
        }
        s.setCoords((int)this.coords.getX() - 20, (int)this.coords.getY() - 20);

        s.staticA.start();

//        GamePlay.getGP().splashes.add(s);

        PhysicsObject phyObj = PhysicsObject.createFromCircSprite(s, GamePlay.getGP().world);
        GamePlay.getGP().objects.add(phyObj);
    }



}

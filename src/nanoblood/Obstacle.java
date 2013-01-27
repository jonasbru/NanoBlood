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
        int rand = (int) (Math.random() * 14);
        if(rand < 1) {
            return new Cancer();
        }
        if(rand < 3) {
            return new Virus();
        }
        if(rand < 5) {
            return new GlobuleBlanc();
        }
        if(rand < 10) {
            return new GlobuleRouge();
        }
        if(rand < 12) {
            return new Bouclier();
        }
        if(rand < 14) {
            return new Heart();
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
        s.setCoords(this.coords);

        s.staticA.start();

        GamePlay.getGP().splashes.add(s);
    }



}

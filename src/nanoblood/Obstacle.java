/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

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



}

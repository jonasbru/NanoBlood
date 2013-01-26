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
        int rand = (int) (Math.random() * 5);
        if(rand < 5) {
            return new Cancer();
        }
        if(rand < 2) {
            return new GlobuleBlanc();
        }
        if(rand < 3) {
            return new Virus();
        }

        return new GlobuleRouge();
    }

    @Override
    public void colideWithPlayer() {
        this.remove = true;
    }



}

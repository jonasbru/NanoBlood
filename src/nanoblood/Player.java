/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

/**
 *
 * @author jonas
 */
public class Player extends Sprite {

    Image staticShip;

    public Player() throws SlickException {
        this.staticShip = Sprite.getImage("sprites/player/static.png");
    }

    @Override
    public Renderable getRenderable() {
        return this.staticShip;
    }

    public void goUp() {
        this.coords.setLocation(this.coords.getX(), this.coords.getY() - 5);
    }

    public void goDown() {
        this.coords.setLocation(this.coords.getX(), this.coords.getY() + 5);
    }

}

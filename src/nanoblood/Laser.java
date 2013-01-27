/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author jonas
 */
public class Laser extends Obstacle{
    
    Image laser;

    public Laser() throws SlickException {
        this.laser = Sprite.getImage("sprites/player/laser_00002.png");

        this.boundingBox = new Rectangle((int) this.coords.getX(), (int) this.coords.getY() + 40, 100, 10);

    }

    @Override
    public Renderable getRenderable() {
        return this.laser;
    }

    @Override
    public void colideWithPlayer() {
    }

}

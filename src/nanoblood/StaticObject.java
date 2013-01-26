/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

/**
 *
 * @author jonas
 */
public abstract class StaticObject extends Sprite{
    public void move(double x, double y) {
        this.getCoords().setLocation(getCoords().getX() + x, getCoords().getY() + y);
    }
}

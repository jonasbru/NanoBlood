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
    public void move(int x, int y) {
        this.getCoords().setLocation(getCoords().getX() + x, getCoords().getY() + y);
        this.boundingBox.setX(boundingBox.getX() + x);
        this.boundingBox.setY(boundingBox.getY() + y);
    }
}

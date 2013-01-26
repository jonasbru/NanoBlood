/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.util;

/**
 *
 * @author Anthony
 */
public interface IObserver {
    
    public void update(IObservable obs, Object obj);
    
}

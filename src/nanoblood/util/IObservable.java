/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.util;

/**
 *
 * @author Anthony
 */
public interface IObservable {
    
    public void addObserver(IObserver o);
    
    public int countObservers();
    
    public void deleteObserver(IObserver o);
    
    public void deleteObservers(IObserver o);
    
    public boolean hasChanged();
    
    public void notifyObserver(IObserver o);
    
    public void notifyObservers();
    
}

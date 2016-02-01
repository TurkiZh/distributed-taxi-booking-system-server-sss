package com.robertnorthard.dtbs.server.layer.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Subject class implementation for the observer pattern.
 * @author robertnorthard
 */
public abstract class Subject {
    
    private Set<Observer> observers;
    
    public Subject(){
        this.observers = new HashSet<>();
    }
    
    /**
     * Register observer to subject.
     * @param observer observer to register.
     */
    public void registerObserver(Observer observer){
        this.observers.add(observer);
    }
    
    /**
     * Unsubscribe observer from the subject.
     * @param observer observer to unsubscribe.
     */
    public void removeObserver(Observer observer){
        this.observers.remove(observer);
    }
    
    /**
     * Notify all observers of state change.
     * @param obj updated object.
     */
    public void notifyObservers(Object obj){
        for(Observer o: this.observers){
            o.update(obj);
        }
    }
}

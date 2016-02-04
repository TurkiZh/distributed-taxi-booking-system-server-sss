/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.layer.model.booking.Booking;
import com.robertnorthard.dtbs.server.layer.model.Taxi;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of Taxi Facade.
 * @author robertnorthard
 */
public class TaxiQueueService implements TaxiQueueFacade{
    
    private static final Logger LOGGER = Logger.getLogger(TaxiQueueService.class.getName());
    
    private boolean threadRunning = false;
    
    /*
        Waiting taxis.
    */
    private List<Taxi> taxisWaiting;
    
    /*
        Taxis currently on job.
    */
    private List<Taxi> taxisOnJob;
    
    /*
        Bookings yet to be fulfilled.
    */
    private List<Booking> waitingBookings;
    
    /*
        Bookings with taxi assinged.
    */
    private List<Booking> activeBookings;
    
    @Override
    public void addTaxi(Taxi taxi) {
        synchronized(TaxiQueueService.class){
            // add taxi to queue based on state.
        }
    }

    /**
     * @return return a list of taxis waiting users.
     */
    @Override
    public List<Taxi> getTaxis() {
        synchronized(TaxiQueueService.class){
            
            List<Taxi> allTaxis = new ArrayList<>();
            allTaxis.addAll(this.taxisWaiting);
            allTaxis.addAll(this.taxisOnJob);
            
            return this.taxisWaiting;
        }
    }

    /**
     * @return number of taxis on waiting list.
     */
    @Override
    public int getWaitingTaxiCount() {
        synchronized(TaxiQueueService.class){ 
            return this.waitingBookings.size();
        }
    }

    /**
     * Add booking to waiting list.
     * @param booking booking to add.
     */
    @Override
    public void addBooking(Booking booking) { 
       synchronized(TaxiQueueService.class){
           this.waitingBookings.add(booking);
       }
    }
    
    /**
     * @return return a list of active bookings.
     */
    @Override
    public List<Booking> getAllActiveBooking() {
        synchronized(TaxiQueueService.class){
           return this.activeBookings;
        }
    }

    /**
     * @param booking removing b
     */
    @Override
    public void removeBooking(Booking booking) {
        synchronized(TaxiQueueService.class){
            if(this.waitingBookings.contains(booking)){
                this.waitingBookings.remove(booking);
            }
        }
    }
    
    /**
     * Algorithm for scheduling and dispatching taxis.
     */
    private void assignTaxi(){
        synchronized(TaxiQueueService.class){
           // No booking or no Taxis available
           if(this.waitingBookings.isEmpty() || this.taxisWaiting.isEmpty()){
               return;
           } 
           
           // multiple passengers?         
           
            // Next booking
           Booking booking = this.waitingBookings.remove(0);
           
           // Taxi to assign to booking
           Taxi taxi = this.taxisWaiting.remove(0);
          
           // booking register taxi as observer.
           // Passenger observe taxi.
           
           // Assign job to taxi.
           booking.setTaxi(taxi);
        }
    }

    @Override
    public void run() {
        
        this.threadRunning = true;
        
        while(this.threadRunning){
            
            this.assignTaxi();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getTaxiInOperationCount() {
        
        synchronized(TaxiQueueService.class){
            return this.taxisWaiting.size() + this.taxisOnJob.size();
        }
        
    }
}

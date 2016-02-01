package com.robertnorthard.dtbs.server.layer.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents Taxi booking.
 * @author robertnorthard
 */
@Entity
@Table(name="BOOKING")
public class Booking implements Serializable{
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    @Column(name="NUMBER_PASSENGERS")
    private int numberPassengers;
    
    @Column(name="COST")
    private double cost;
    
    @OneToOne
    @JoinColumn(name="TAXI_ID")
    private Taxi taxi;
    
    @OneToOne
    @JoinColumn(name="ROUTE_ID")
    private Route route;

    public Booking() {}
    
    public Booking(Taxi taxi, Route route, int numberPassengers){
        this.taxi = taxi;
        this.route = route;
        this.numberPassengers = numberPassengers;
        this.cost = this.calculateCost();
        
        this.timestamp = new Date();
    }
    
    /**
     * Calculate cost of taxi.
     * @return cost of taxi booking.
     */
    public double calculateCost(){
        return this.taxi.getCostPerMile() * this.getRoute().getDistance() + 5;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId(){
        return this.id;
                
    }
    
    /**
     * Set id.
     * @param id id to set. 
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * @return the timestamp
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the numberPassengers
     */
    public int getNumberPassengers() {
        return numberPassengers;
    }

    /**
     * @param numberPassengers the numberPassengers to set
     */
    public void setNumberPassengers(int numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    } 

    /**
     * @return the taxi
     */
    public Taxi getTaxi() {
        return taxi;
    }

    /**
     * @param taxi the taxi to set
     */
    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    /**
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }
}

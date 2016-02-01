package com.robertnorthard.dtbs.server.layer.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Represent a Taxi route.
 * @author robertnorthard
 */
@Entity
@Table(name = "ROUTE")
public class Route implements Serializable{
    
    @Column(name="ID")
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @JoinColumn(name="START_LOCATION")
    private Location startLocation;
    
    @JoinColumn(name="END_LOCATION")
    private Location endLocation;
    
    @Column(name="DISTANCE")
    private double distance;
    
    @Column(name="ESTIMATED_TRAVEL_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimateTravelTime;
    
    @Transient
    private List<Location> route;
    
    public Route() {
    }

    /**
     * 
     * @param startLocation start location of route.
     * @param endLocation end location of route.
     * @param distance route distance.
     * @param route route of path.
     * @param estimateTravelTime estimated travel time between start and end location.
     */
    public Route(Location startLocation, Location endLocation, 
            double distance, List<Location> route, Date estimateTravelTime){
        
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.route = route;
        this.estimateTravelTime = estimateTravelTime;
     
    }
    
    /**
     * @return the startLocation
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * @param startLocation the startLocation to set
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * @return the endLocation
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * @param endLocation the endLocation to set
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the route
     */
    public List<Location> getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(List<Location> route) {
        this.route = route;
    }

    /**
     * @return the estimateTravelTime
     */
    
    public Date getEstimateTravelTime() {
        return estimateTravelTime;
    }

    /**
     * @param estimateTravelTime the estimateTravelTime to set
     */
    public void setEstimateTravelTime(Date estimateTravelTime) {
        this.estimateTravelTime = estimateTravelTime;
    }
}

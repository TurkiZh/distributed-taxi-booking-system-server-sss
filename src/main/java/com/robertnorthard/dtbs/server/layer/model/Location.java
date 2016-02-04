package com.robertnorthard.dtbs.server.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represent a location by latitude and longitude.
 * @author robertnorthard
 */
@Entity
@Table(name="LOCATION")
public class Location implements Serializable{
    
    @Column(name="ID")
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="LATITUDE")
    private double latitude;
    
    @Column(name="LONGITUDE")
    private double longitude;

    public Location() {
        // Empty constructor required by JPA.
    }
    
    /**
     * Constructor for class Location.
     * @param latitude latitude.
     * @param longitude longitude.
     * @throws IllegalArgumentException latitude or longitude is invalid. 
     */
    public Location(double latitude, double longitude){
        
        if(!Location.validateCoordinates(latitude,longitude)){
            throw new IllegalArgumentException("Invalid latitude or longitude.");
        }
        
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Validate latitude and longitude 
     * @param latitude latitude.
     * @param longitude longitude.
     * @return true if coordinates valid else false.
     */
    public static boolean validateCoordinates(double latitude, double longitude){
        return Location.validateLatitude(latitude) && Location.validateLongitude(longitude);
    }

    /**
     * Validate latitude.
     * @param latitude latitude.
     * @return true if valid else false.
     */
    public static boolean validateLatitude(double latitude){
        return !(latitude > 90 || latitude < -90);
    }
    
    /**
     * Validate longitude.
     * @param longitude longitude.
     * @return true if valid else false.
     */
    public static boolean validateLongitude(double longitude){
        return !(longitude < -180 || longitude > 180);
    }
    
    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     * @throws IllegalArgumentException latitude is not between 90 and -90. 
     */
    public void setLatitude(double latitude) {
        
        if(Location.validateLatitude(latitude)){
            this.latitude = latitude;
        }else{
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     * @throws IllegalArgumentException longitude is not between 90 and -90.
     */
    public void setLongitude(double longitude) {
        if(Location.validateLatitude(longitude)){
            this.longitude = longitude;
        }else{
            throw new IllegalArgumentException("Latitude must be between -180 and 180.");
        }
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        return this.latitude + "," + this.longitude;
    }
}
package com.robertnorthard.dtbs.server.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Observable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents a Taxi.
 * @author robertnorthard
 */
@Entity
@Table(name="TAXI")
public class Taxi extends Observable implements Serializable{
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    
    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="VEHICLE_ID")
    private Vehicle vehicle; 
    
    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    // Last known taxi location.
    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="TAXI_LOCATION_ID")
    private Location location;
    
    @Transient
    private TaxiState taxiState;
    
    public Taxi() {
        // Empty constructor required by JPA.
    }
    
    /**
     * 
     * @param vehicle the taxis vehicle.
     * @param account the driver of the taxi.
     */
    public Taxi(Vehicle vehicle, Account account){
        this.vehicle = vehicle;
        this.account = account;
        
        this.location = new Location();
    }
    
    /**
     * Return flat rate cost of the taxi, excluding additional costs due to millage.
     * @return 
     */
    @JsonIgnore
    public double getCostPerMile(){
        return this.getVehicle().getCostPerMile();
    }

    /**
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.setId((Long) id);
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
    /**
     * Update taxi's current location.
     * @param location new location.
     */
    public void updateLocation(Location location){
        if(location != null){
            this.location.setLatitude(location.getLatitude());
            this.location.setLongitude(location.getLongitude());
        }else{
            throw new IllegalArgumentException("Location can not be null.");
        }
    }

    /**
     * @return the taxiState
     */
    public TaxiState getTaxiState() {
        return taxiState;
    }

    /**
     * @param taxiState the taxiState to set
     */
    public void setTaxiState(TaxiState taxiState) {
        this.taxiState = taxiState;
    }
}

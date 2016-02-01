package com.robertnorthard.dtbs.server.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Abstract vehicle class
 * @author robertnorthard 
 */
@Entity
@Table(name="VEHICLE")
public class Vehicle implements Serializable
{
    @Id
    @Column(name="NUMBER_PLATE")
    private String numberplate;
   
    // number of seats including driver
    @Column(name="NUMBER_SEATS")
    private int numberSeats;
    
    @JoinColumn(name="VEHICLE_TYPE_ID")
    @ManyToOne(cascade = CascadeType.MERGE)
    private VehicleType vehicleType;
    
    public Vehicle() {}
    
    /**
     * Constructor for class vehicle.
     * @param numberplate vehicle numberplate.
     * @param numberSeats vehicle seat capacity.
     * @param vehicleType type of vehicle.
     */
    public Vehicle(String numberplate, int numberSeats, VehicleType vehicleType){
        this.numberplate = numberplate;
        this.numberSeats = numberSeats;
        this.vehicleType = vehicleType;
    }
    
    /**
     * Return flat rate cost of vehicle.
     * @return flat rate cost of vehicle.
     */
    @JsonIgnore
    public double getCostPerMile(){
        return this.vehicleType.getCost();
    }
    
    /**
     * @return the numberplate
     */
    public String getNumberplate() {
        return numberplate;
    }

    /**
     * @param numberplate the numberplate to set
     */
    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }

    /**
     * @return the numberSeats
     */
    public int getNumberSeats() {
        return numberSeats;
    }

    /**
     * @param numberSeats the numberSeats to set
     */
    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    /**
     * @return the vehicleType
     */
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    /**
     * 
     * @param numberSeats number of seats required.
     * @return true if vehicle has enough free seats.
     */
    public boolean checkseatAvailability(int numberSeats){
        return this.numberSeats - 1 - numberSeats > 0;
    }
}

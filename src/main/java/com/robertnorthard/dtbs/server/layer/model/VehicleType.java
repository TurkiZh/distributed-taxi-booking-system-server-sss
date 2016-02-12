package com.robertnorthard.dtbs.server.layer.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represent a vehicle type.
 *
 * @author robertnorthard
 */
@Entity
@Table(name = "VEHICLE_TYPE")
public class VehicleType implements Serializable {

    @Transient
    private static final long serialVersionUID = 0L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "COST")
    private double cost;

    public VehicleType() {
        // Empty constructor required by JPA.
    }

    public VehicleType(String name, String manufacturer, String model, double cost) {
        this.name = name;
        this.cost = cost;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
}

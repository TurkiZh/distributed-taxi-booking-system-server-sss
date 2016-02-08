package com.robertnorthard.dtbs.server.layer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represent an address. Address has a location and address name.
 *
 * @author robertnorthard
 */
@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "ADDRESS")
    private String address;

    public Address() {
        // Needed by JPA.
    }

    /**
     * Constructor for class address.
     *
     * @param address address e.g. 30 Cheviots, Hatfield, AL10 8JD.
     * @param location latitude and longitude location of address.
     */
    public Address(String address, Location location) {
        this.address = address;
        this.location = location;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return record id.
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id id.
     */
    public void setId(Long id) {
        this.id = id;
    }
}

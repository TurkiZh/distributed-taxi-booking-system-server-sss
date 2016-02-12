package com.robertnorthard.dtbs.server.layer.model.booking;

import com.robertnorthard.dtbs.server.layer.persistence.data.mappers.booking.JpaBookingStateDataConverter;
import com.robertnorthard.dtbs.server.layer.persistence.data.mappers.booking.JsonBookingStateDataConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.robertnorthard.dtbs.server.layer.model.Account;
import com.robertnorthard.dtbs.server.layer.model.Route;
import com.robertnorthard.dtbs.server.layer.model.taxi.Taxi;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Represents Taxi booking.
 *
 * @author robertnorthard
 */
@Entity
@Table(name = "BOOKING")
@NamedQueries({
    @NamedQuery(
            name = "Booking.findBookingsInState",
            query = "SELECT b FROM Booking b WHERE b.state = :state"
    ),
    @NamedQuery(
            name = "Booking.findBookingsForPassenger",
            query = "SELECT b FROM Booking b WHERE b.passenger.username = :username"
    ),
    @NamedQuery(
            name = "Booking.findBookingsForDriver",
            query = "SELECT b FROM Booking b WHERE b.taxi.account.username = :username"
    ),
    @NamedQuery(
            name = "Booking.findBookingsforUserInState",
            query = "SELECT b FROM Booking b WHERE b.passenger.username = :username AND NOT (b.state = :state)"
    )
})
public class Booking implements Serializable {

    @Transient
    private static final long serialVersionUID = -1373406783231928690L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "NUMBER_PASSENGERS")
    private int numberPassengers;

    @Column(name = "COST")
    private double cost;

    @ManyToOne
    @JoinColumn(name = "PASSENGER_USERNAME")
    private Account passenger;

    @ManyToOne
    @JoinColumn(name = "TAXI_ID")
    private Taxi taxi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROUTE_ID")
    private Route route;

    @Column(name = "BOOKING_STATE")
    @Convert(converter = JpaBookingStateDataConverter.class)
    private BookingState state;

    // booking states
    @JsonIgnore
    @Transient
    private static BookingState awaitingTaxiBookingState = new AwaitingTaxiBookingState();
    @JsonIgnore
    @Transient
    private static BookingState cancelledBookingState = new CancelledBookingState();
    @JsonIgnore
    @Transient
    private static BookingState taxiDispatchedBookingState = new TaxiDispatchedBookingState();
    @JsonIgnore
    @Transient
    private static BookingState passengerPickedUpBookingState = new PassengerPickedUpBookingState();
    @JsonIgnore
    @Transient
    private static BookingState completedTaxiBookingState = new CompletedBookingState();

    public Booking() {
        // Empty constructor required by JPA.
    }

    /**
     * Constructor for class booking.
     *
     * @param passenger passenger for order.
     * @param route proposed route for taxi.
     * @param numberPassengers number passengers for booking
     */
    public Booking(Account passenger, Route route, int numberPassengers) {
        this.passenger = passenger;
        this.route = route;
        this.numberPassengers = numberPassengers;
        this.timestamp = new Date();
        this.state = Booking.getAwaitingTaxiBookingState();
        this.cost = this.estimateCost(0.3);
    }

    /**
     * Constructor for instantiating a booking class and immediately dispatching
     * a taxi.
     *
     * @param passenger passenger for order.
     * @param route proposed route for taxi.
     * @param numberPassengers number passengers for booking
     * @param taxi taxi for booking.
     */
    public Booking(Account passenger, Route route, int numberPassengers, Taxi taxi) {
        this.passenger = passenger;
        this.route = route;
        this.numberPassengers = numberPassengers;
        this.timestamp = new Date();
        this.taxi = taxi;
        this.state = Booking.getTaxiDispatchedBookingState();
        this.cost = this.calculateCost();
    }

    public void cancelBooking() {
        this.state.cancelBooking(this);
    }

    public void cancelTaxi() {
        this.state.cancelTaxi(this);
    }

    public void dispatchTaxi(Taxi taxi) {
        this.state.dispatchTaxi(this, taxi);
    }

    public void dropOffPassenger(Date time) {
        this.state.dropOffPassenger(this, time);
    }

    public void pickupPassenger(Date time) {
        this.state.pickupPassenger(this, time);
    }

    /**
     * Calculate cost of taxi using taxi cost per mile.
     *
     * @return cost of taxi booking.
     */
    public final double calculateCost() {
        return this.estimateCost(this.taxi.getCostPerMile());
    }

    /**
     * Estimate cost for taxi using set
     *
     * @param scalar cost per mile.
     * @return estimate of taxi cost using a fixed cost per mile.
     */
    public final double estimateCost(double scalar) {
        return scalar * this.route.getDistanceInMiles() * this.route.getTimeInMinutes();
    }

    /**
     * @return id of booking.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Set id.
     *
     * @param id id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the timestamp
     */
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
        this.cost = this.calculateCost();
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

    /**
     * @return the passenger
     */
    public Account getPassenger() {
        return passenger;
    }

    /**
     * @param passenger the passenger to set
     */
    public void setPassenger(Account passenger) {
        this.passenger = passenger;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the state
     */
    @JsonSerialize(using = JsonBookingStateDataConverter.class)
    public BookingState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(BookingState state) {
        this.state = state;
    }

    public static BookingState getCancelledBookingState() {
        return Booking.cancelledBookingState;
    }

    public static final BookingState getAwaitingTaxiBookingState() {
        return Booking.awaitingTaxiBookingState;
    }

    public static final BookingState getTaxiDispatchedBookingState() {
        return Booking.taxiDispatchedBookingState;
    }

    public static BookingState getPassengerPickedUpBookingState() {
        return Booking.passengerPickedUpBookingState;
    }

    public static BookingState getCompletedTaxiBookingState() {
        return Booking.completedTaxiBookingState;
    }
}

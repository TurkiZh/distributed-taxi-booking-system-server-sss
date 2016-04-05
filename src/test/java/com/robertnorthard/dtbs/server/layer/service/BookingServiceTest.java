package com.robertnorthard.dtbs.server.layer.service;

import com.robertnorthard.dtbs.server.common.exceptions.AccountAuthenticationFailed;
import com.robertnorthard.dtbs.server.common.exceptions.InvalidBookingException;
import com.robertnorthard.dtbs.server.common.exceptions.RouteNotFoundException;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;
import com.robertnorthard.dtbs.server.layer.service.entities.AccountRole;
import com.robertnorthard.dtbs.server.layer.service.entities.Location;
import com.robertnorthard.dtbs.server.layer.service.entities.Route;
import com.robertnorthard.dtbs.server.layer.service.entities.Vehicle;
import com.robertnorthard.dtbs.server.layer.service.entities.VehicleType;
import com.robertnorthard.dtbs.server.layer.service.entities.booking.Booking;
import com.robertnorthard.dtbs.server.layer.service.entities.taxi.Taxi;
import com.robertnorthard.dtbs.server.layer.persistence.BookingDao;
import com.robertnorthard.dtbs.server.layer.persistence.RouteDao;
import com.robertnorthard.dtbs.server.layer.persistence.TaxiDao;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;
import com.robertnorthard.dtbs.server.layer.utils.gcm.GcmClient;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author robertnorthard
 */
public class BookingServiceTest {

    private final BookingDao bookingDao;
    private final AccountFacade accountFacade;
    private final RouteDao routeDao;
    private final TaxiDao taxiDao;
    private final GoogleDistanceMatrixFacade googleDistanceMatrixFacade;
    private final GcmClient gcmClient;
    private final TaxiService taxiService;

    private final BookingService bookingService;

    private VehicleType type;
    private Vehicle vehicle;
    private Account passenger;
    private Route route;
    private Account driver;
    private Taxi taxi;
    private Booking booking;

    public BookingServiceTest() {
        this.bookingDao = mock(BookingDao.class);
        this.accountFacade = mock(AccountFacade.class);
        this.routeDao = mock(RouteDao.class);
        this.taxiDao = mock(TaxiDao.class);
        this.googleDistanceMatrixFacade = mock(GoogleDistanceMatrixFacade.class);
        this.gcmClient = mock(GcmClient.class);
        this.taxiService = mock(TaxiService.class);

        this.bookingService = new BookingService(this.bookingDao,
                this.accountFacade,
                this.routeDao,
                this.taxiDao,
                this.googleDistanceMatrixFacade,
                this.gcmClient,
                this.taxiService);
    }

    @Before
    public void setUp() {
        passenger = new Account("timsmith", "Tim", "Smith", "password", "tim_smith@example.com", "07526888826");
        passenger.setRole(AccountRole.PASSENGER);

        driver = new Account("johndoe", "John", "Doe", "simple_password", "john_doe@example.com", "07888888826");
        driver.setRole(AccountRole.DRIVER);

        type = new VehicleType("Taxi", "Ford", "Focus", 0.3);
        vehicle = new Vehicle("AS10 AJ", 3, type);
        taxi = new Taxi(vehicle, driver);

        route = new Route();
        route.setDistance(10);
        route.setEstimateTravelTime(4198);

        booking = new Booking(passenger, route, 2);
    }

    /**
     * Test of findBooking method, of class BookingService. Test: valid booking.
     */
    @Test
    public void testFindBooking1() {
        when(this.bookingDao.findEntityById(1L)).thenReturn(this.booking);

        Booking expResult = this.booking;
        Booking result = this.bookingService.findBooking(1L);

        assertEquals(expResult, result);
    }

    /**
     * Test of findBooking method, of class BookingService. Test: booking not found.
     */
    @Test
    public void testFindBooking2() {
        when(this.bookingDao.findEntityById(1L)).thenReturn(null);

        Booking expResult = null;
        Booking result = this.bookingService.findBooking(1L);

        assertEquals(expResult, result);
    }

    /**
     * Test of makeBooking method, of class BookingService. Test: make booking - username not found.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMakeBooking1() throws Exception {

        BookingDto bookingDto = new BookingDto();

        this.bookingService.makeBooking(bookingDto);

        fail("Method should throw Illegal Argument Exception.");
    }

    /**
     * Test of makeBooking method, of class BookingService. Test: Route not found.
     */
    @Test(expected = RouteNotFoundException.class)
    public void testMakeBooking2() throws Exception {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setPassengerUsername("timsmith");
        bookingDto.setEndLocation(new Location(89, 179));
        bookingDto.setStartLocation(new Location(89, 180));
        bookingDto.setNumberPassengers(1);

        when(this.accountFacade.findAccount(
                bookingDto.getPassengerUsername()))
                .thenReturn(this.passenger);

        when(this.googleDistanceMatrixFacade.getRouteInfo(
                bookingDto.getStartLocation(),
                bookingDto.getEndLocation())).thenReturn(null);

        this.bookingService.makeBooking(bookingDto);

        fail("Method should throw authentication failed.");
    }

    /**
     * Test of makeBooking method, of class BookingService. Test: Journey more than 50 miles.
     */
    @Test(expected = InvalidBookingException.class)
    public void testMakeBooking3() throws Exception {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setPassengerUsername("timsmith");
        bookingDto.setEndLocation(new Location(89, 179));
        bookingDto.setStartLocation(new Location(90, 180));
        bookingDto.setNumberPassengers(1);

        when(this.accountFacade.findAccount(
                bookingDto.getPassengerUsername()))
                .thenReturn(this.passenger);

        when(this.googleDistanceMatrixFacade.getRouteInfo(
                bookingDto.getStartLocation(),
                bookingDto.getEndLocation())).thenReturn(null);

        this.bookingService.makeBooking(bookingDto);

        fail("Method should throw journeys cannot be more than 50 miles.");
    }

    /**
     * Test of findBooking method, of class BookingService.
     * Test: Authentication failed.
     */
    @Test(expected = AccountAuthenticationFailed.class)
    public void testMakeBooking4() throws Exception {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setPassengerUsername("timsmith");
        bookingDto.setEndLocation(new Location(89, 179));
        bookingDto.setStartLocation(new Location(89, 180));
        bookingDto.setNumberPassengers(1);

        when(this.accountFacade.findAccount(
                bookingDto.getPassengerUsername()))
                .thenReturn(null);

        when(this.googleDistanceMatrixFacade.getRouteInfo(
                bookingDto.getStartLocation(),
                bookingDto.getEndLocation())).thenReturn(this.route);

        this.bookingService.makeBooking(bookingDto);

        fail("Method should throw authentication failed.");
    }

}

package com.robertnorthard.dtbs.server.layer.persistence.data.mappers.booking;

import com.robertnorthard.dtbs.server.layer.model.booking.AwaitingTaxiBookingState;
import com.robertnorthard.dtbs.server.layer.model.booking.BookingState;
import com.robertnorthard.dtbs.server.layer.model.booking.BookingStates;
import com.robertnorthard.dtbs.server.layer.model.booking.CancelledBookingState;
import com.robertnorthard.dtbs.server.layer.model.booking.CompletedBookingState;
import com.robertnorthard.dtbs.server.layer.model.booking.PassengerPickedUpBookingState;
import com.robertnorthard.dtbs.server.layer.model.booking.TaxiDispatchedBookingState;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Mapping between database entity and state object.
 *
 * @author robertnorthard
 */
@Converter
public class JpaBookingStateDataConverter implements AttributeConverter<BookingState, String> {

    @Override
    public String convertToDatabaseColumn(BookingState attribute) {

        // use java reflection here instead?
        if (attribute instanceof AwaitingTaxiBookingState) {
            return BookingStates.AWAITING_TAXI.toString();

        } else if (attribute instanceof CancelledBookingState) {
            return BookingStates.CANCELED_BOOKING.toString();

        } else if (attribute instanceof CompletedBookingState) {
            return BookingStates.COMPLETED_BOOKING.toString();

        } else if (attribute instanceof PassengerPickedUpBookingState) {
            return BookingStates.PASSENGER_PICKED_UP.toString();

        } else if (attribute instanceof TaxiDispatchedBookingState) {
            return BookingStates.TAXI_DISPATCHED.toString();
        }

        throw new IllegalArgumentException("Cannot convert object to data.");
    }

    @Override
    public BookingState convertToEntityAttribute(String dbData) {

        // use java reflection here instead?
        if (dbData.equals(BookingStates.AWAITING_TAXI.toString())) {
            return new AwaitingTaxiBookingState();

        } else if (dbData.equals(BookingStates.CANCELED_BOOKING.toString())) {
            return new CancelledBookingState();

        } else if (dbData.equals(BookingStates.COMPLETED_BOOKING.toString())) {
            return new CompletedBookingState();

        } else if (dbData.equals(BookingStates.TAXI_DISPATCHED.toString())) {
            return new TaxiDispatchedBookingState();

        } else if (dbData.equals(BookingStates.PASSENGER_PICKED_UP.toString())) {
            return new PassengerPickedUpBookingState();
        }

        throw new IllegalArgumentException("Cannot convert database entity to object.");

    }
}

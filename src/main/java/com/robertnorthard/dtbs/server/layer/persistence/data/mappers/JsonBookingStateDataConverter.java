package com.robertnorthard.dtbs.server.layer.persistence.data.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.robertnorthard.dtbs.server.layer.model.booking.BookingState;
import java.io.IOException;

/**
 * JSON serializer for Booking state class.
 * 
 * @author robertnorthard
 */
public class JsonBookingStateDataConverter extends JsonSerializer<BookingState> {

    @Override
    public void serialize(BookingState t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString(new JpaBookingStateDataConverter().convertToDatabaseColumn(t));
    }
    
}

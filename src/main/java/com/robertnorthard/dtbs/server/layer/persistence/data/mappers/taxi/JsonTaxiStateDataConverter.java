package com.robertnorthard.dtbs.server.layer.persistence.data.mappers.taxi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.robertnorthard.dtbs.server.layer.model.taxi.TaxiState;
import java.io.IOException;

/**
 * JSON serializer Taxi booking state class.
 * 
 * @author robertnorthard
 */
public class JsonTaxiStateDataConverter extends JsonSerializer<TaxiState> {

    @Override
    public void serialize(TaxiState t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString(new JpaTaxiStateDataConverter().convertToDatabaseColumn(t));
    }
}

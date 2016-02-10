package com.robertnorthard.dtbs.server.layer.persistence.data.mappers.taxi;

import com.robertnorthard.dtbs.server.layer.model.taxi.OffDutyTaxiState;
import com.robertnorthard.dtbs.server.layer.model.taxi.OnDutyTaxiState;
import com.robertnorthard.dtbs.server.layer.model.taxi.AcceptedJobTaxiState;
import com.robertnorthard.dtbs.server.layer.model.taxi.TaxiState;
import com.robertnorthard.dtbs.server.layer.model.taxi.TaxiStates;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author robertnorthard
 */
@Converter
public class JpaTaxiStateDataConverter implements AttributeConverter<TaxiState, String> {

    @Override
    public String convertToDatabaseColumn(TaxiState attribute) {

        // use java reflection here instead?
        if (attribute instanceof AcceptedJobTaxiState) {
            return TaxiStates.ON_JOB.toString();

        } else if (attribute instanceof OffDutyTaxiState) {
            return TaxiStates.OFF_DUTY.toString();

        } else if (attribute instanceof OnDutyTaxiState) {
            return TaxiStates.ON_DUTY.toString();

        }

        throw new IllegalArgumentException("Cannot convert object to data.");
    }

    @Override
    public TaxiState convertToEntityAttribute(String dbData) {
        
        if(dbData.equals(TaxiStates.OFF_DUTY.toString())){
            return new OffDutyTaxiState();
        }else if(dbData.equals(TaxiStates.ON_DUTY.toString())){
            return new OnDutyTaxiState();
        }else if(dbData.equals(TaxiStates.ON_JOB.toString())){
            return new AcceptedJobTaxiState();
        }

        throw new IllegalArgumentException("Cannot convert database entity to object.");

    }
}

package com.robertnorthard.dtbs.server.layer.service.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.service.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;

/**
 *  Validate passenger specification for a booking dto.
 * 
 * @author robertnorthard
 */
public class ValidatePassengerNumberSpecification extends CompositeSpecification<BookingDto>{

    
    public ValidatePassengerNumberSpecification(ValidatorResult result){
        super(result);
    }
    
    @Override
    public boolean isSatisfiedBy(BookingDto candidate) {

        if(candidate == null){
            throw new IllegalArgumentException("Candidate cannot be null.");
        }
        
        if(candidate.getNumberPassengers() <= 0){
            this.getErrorResult().addError("Passenger count must be more than 0.");
            return false;
        }

        return true;
    }
}
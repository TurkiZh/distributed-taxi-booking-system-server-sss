package com.robertnorthard.dtbs.server.layer.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;

/**
 *  Validate username specification for a booking dto.
 * 
 * @author robertnorthard
 */
public class ValidUsernameSpecification extends CompositeSpecification<BookingDto>{

    
    public ValidUsernameSpecification(ValidatorResult result){
        super(result);
    }
    
    @Override
    public boolean isSatisfiedBy(BookingDto candidate) {

        if(candidate == null){
            throw new IllegalArgumentException("Candidate cannot be null.");
        }

        return true;
    }
}
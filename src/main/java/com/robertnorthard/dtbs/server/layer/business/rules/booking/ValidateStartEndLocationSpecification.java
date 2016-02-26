package com.robertnorthard.dtbs.server.layer.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;

/**
 * Validate start/end location specification.
 * 
 * @author robertnorthard
 */
public class ValidateStartEndLocationSpecification extends CompositeSpecification<BookingDto>{

    
    public ValidateStartEndLocationSpecification(ValidatorResult result){
        super(result);
    }
    
    @Override
    public boolean isSatisfiedBy(BookingDto candidate) {

        if(candidate.getEndLocation() == null || candidate.getStartLocation() == null){
            throw new IllegalArgumentException("Candidate cannot be null.");
        }
        
        if(candidate.getStartLocation().equals(candidate.getEndLocation())){
            this.getErrorResult().addError("Cannot book a taxi with destination of current location.");
            return false;
        }
        
        return true;
    }
}
package com.robertnorthard.dtbs.server.layer.service.business.rules.booking;

import com.robertnorthard.dtbs.server.layer.service.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.service.entities.Location;
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
        
        double distanceAtoB = Location.getDistance(candidate.getStartLocation(), candidate.getEndLocation());
        
        // if destination within 200 metres fail booking validation.
        if(distanceAtoB <= 200.0){
            this.getErrorResult()
                    .addError(
                    "Do you need a taxi? You are within 200 metres of your destination");
            return false;
        }
        
        if(distanceAtoB >= 80467.2){
             this.getErrorResult()
                    .addError(
                    "Journeys cannot be more than 50 miles.");
            return false;
        }
        
        return true;
    }
}
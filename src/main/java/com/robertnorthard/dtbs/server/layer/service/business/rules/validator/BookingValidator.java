package com.robertnorthard.dtbs.server.layer.service.business.rules.validator;

import com.robertnorthard.dtbs.server.layer.service.business.rules.booking.ValidUsernameSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.booking.ValidatePassengerNumberSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.booking.ValidateStartEndLocationSpecification;
import com.robertnorthard.dtbs.server.layer.persistence.dto.BookingDto;

/**
 * Account validator is responsible for aggregating all business rules (specifications)
 * related to validating a user account.
 * 
 * @author robertnorthard
 */
public class BookingValidator extends Validator<BookingDto> {
    
    public BookingValidator(){
        super(new ValidatorContext());
    }
    
   /**
     * Valid provided entity by realising appropriate business specification rules.
     * 
     * @param booking booking to validate.
     * @return true if validated successfully  else false.
     */
    @Override
    public boolean validate(BookingDto booking){
        ValidatorResult result = this.getValidatorResult();
        
        return new ValidUsernameSpecification(result)
                .and(new ValidateStartEndLocationSpecification(result))
                .and(new ValidatePassengerNumberSpecification(result))
                .isSatisfiedBy(booking);
    }
}

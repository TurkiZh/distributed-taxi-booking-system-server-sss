package com.robertnorthard.dtbs.server.layer.business.rules.account;

import com.robertnorthard.dtbs.server.layer.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.model.Account;

/**
 * Valid Phone number specification constraint class.
 * 
 * @author robertnorthard
 */
public class ValidPhoneNumberSpecification extends CompositeSpecification<Account> {

    public ValidPhoneNumberSpecification(ValidatorResult result) {
        super(result);
    }

    @Override
    public boolean isSatisfiedBy(Account candidate) {

        if (candidate == null) {
            throw new IllegalArgumentException("Candidate cannot be null.");
        }

        if (candidate.getPhoneNumber() == null) {
            this.getErrorResult().addError("Phone number must be provided.");
            return false;
        }

        if (!candidate.getPhoneNumber().matches("\\d{11}")) {
            this.getErrorResult().addError("Phone number invalid.");
            return false;
        }
        
        return true;
    }

}

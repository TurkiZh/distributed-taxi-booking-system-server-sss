package com.robertnorthard.dtbs.server.layer.service.business.rules.account;

import com.robertnorthard.dtbs.server.layer.service.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;

/**
 * Username cannot match password specification.
 * 
 * @author robertnorthard
 */
public class UsernameCannotMatchPasswordSpecification extends CompositeSpecification<Account>{

    public UsernameCannotMatchPasswordSpecification(ValidatorResult result){
        super(result);
    }
    
    @Override
    public boolean isSatisfiedBy(Account candidate) {
        if(candidate.getPassword().equals(candidate.getUsername())){
            this.getErrorResult().addError("Password and username cannot match.");
            return false;
        }else{
            return true;
        }
    }
}

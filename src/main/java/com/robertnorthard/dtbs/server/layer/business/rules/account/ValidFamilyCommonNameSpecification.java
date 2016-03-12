package com.robertnorthard.dtbs.server.layer.business.rules.account;

import com.robertnorthard.dtbs.server.layer.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.model.Account;

/**
 *  Validate username specification. 
 * 
 * @author robertnorthard
 */
public class ValidFamilyCommonNameSpecification extends CompositeSpecification<Account>{

    
    public ValidFamilyCommonNameSpecification(ValidatorResult result){
        super(result);
    }
    
    @Override
    public boolean isSatisfiedBy(Account candidate) {

        if(candidate.getCommonName() == null || candidate.getFamilyName() == null){
            throw new IllegalArgumentException("Family and common name cannot be null.");
        }
        
        if (candidate.getCommonName().equals("") || candidate.getFamilyName().equals("")) {
            this.getErrorResult().addError("family and common name cannot be empty.");
            return false;
        }else{
            return true;
        }
    }
}

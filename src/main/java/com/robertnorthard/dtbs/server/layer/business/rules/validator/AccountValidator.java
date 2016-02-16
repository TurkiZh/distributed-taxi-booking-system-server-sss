package com.robertnorthard.dtbs.server.layer.business.rules.validator;

import com.robertnorthard.dtbs.server.layer.business.rules.account.PasswordConstrantSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.account.UniqueUsernameSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.account.UsernameCannotMatchPasswordSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.account.ValidEmailSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.account.ValidPhoneNumberSpecification;
import com.robertnorthard.dtbs.server.layer.business.rules.account.ValidUsernameSpecification;
import com.robertnorthard.dtbs.server.layer.model.Account;

/**
 * Account validator is responsible for aggregating all business rules (specifications)
 * related to validating a user account.
 * 
 * @author robertnorthard
 */
public class AccountValidator extends Validator<Account> {
    
    public AccountValidator(){
        super(new ValidatorContext());
    }
    
   /**
     * Valid provided entity by realising appropriate business specification rules.
     * 
     * @param account account to validate.
     * @return true if validated successfully  else false.
     */
    @Override
    public boolean validate(Account account){
        ValidatorResult result = this.getValidatorResult();
        
        return new PasswordConstrantSpecification(result)
                .and(new UsernameCannotMatchPasswordSpecification(result))
                .and(new ValidUsernameSpecification(result))
                .and(new ValidEmailSpecification(result))
                .and(new UniqueUsernameSpecification(result))
                .and(new ValidPhoneNumberSpecification(result))
                .isSatisfiedBy(account);
    }
}

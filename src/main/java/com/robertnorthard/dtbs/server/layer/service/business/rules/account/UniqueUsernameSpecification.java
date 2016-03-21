package com.robertnorthard.dtbs.server.layer.service.business.rules.account;

import com.robertnorthard.dtbs.server.layer.service.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;
import com.robertnorthard.dtbs.server.layer.persistence.AccountDao;

/**
 *
 * @author robertnorthard
 */
public class UniqueUsernameSpecification extends CompositeSpecification<Account> {

    private AccountDao accountDao;
    
    public UniqueUsernameSpecification(ValidatorResult result){
        super(result);
        this.accountDao = new AccountDao();
    }
    
    public UniqueUsernameSpecification(AccountDao accountDao, ValidatorResult result){
        super(result);
        this.accountDao = accountDao;
    }
    
    @Override
    public boolean isSatisfiedBy(Account candidate) {
        if(this.accountDao.findEntityById(candidate.getUsername()) == null){
            return true;
        }else{
            this.getErrorResult().addError("Account with username already exists.");
            return false;
        }
    }
}

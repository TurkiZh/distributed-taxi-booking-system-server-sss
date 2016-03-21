package com.robertnorthard.dtbs.server.layer.service.business.rules.account;

import com.robertnorthard.dtbs.server.layer.service.business.rules.CompositeSpecification;
import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;
import com.robertnorthard.dtbs.server.layer.service.entities.Account;
import com.robertnorthard.dtbs.server.layer.utils.mail.MailStrategy;
import com.robertnorthard.dtbs.server.layer.utils.mail.SmtpMailStrategy;

/**
 * Validate email specification. 
 * 
 * @author robertnorthard
 */
public class ValidEmailSpecification extends CompositeSpecification<Account> {

    private MailStrategy mailStrategy;

    public ValidEmailSpecification(ValidatorResult result) {
        super(result);
        this.mailStrategy = new SmtpMailStrategy();
    }

    public ValidEmailSpecification(ValidatorResult result, MailStrategy mailStrategy) {
        super(result);
        this.mailStrategy = mailStrategy;
    }

    @Override
    public boolean isSatisfiedBy(Account candidate) {
        if (this.mailStrategy.isValidEmail(candidate.getEmail())) {
            return true;
        } else {
            this.getErrorResult().addError("Invalid email address.");
            return false;
        }
    }
}

package com.robertnorthard.dtbs.server.layer.business.rules.validator;

import java.util.List;

/**
 * Interface to represent validator results.
 * 
 * @author robertnorthard
 */
public interface ValidatorResult {
    
    /**
     * Add error message.
     * 
     * @param error error message to add.
     */
    public void addError(String error);
    
    /**
     * Return list of errors.
     * 
     * @return list of errors.
     */
    public List<String> getErrors();
    
    /**
     * Return true if failed business rules, else false.
     * 
     * @return true if failed business rules, else false. 
     */
    public boolean isFailedBusinessRules();
}

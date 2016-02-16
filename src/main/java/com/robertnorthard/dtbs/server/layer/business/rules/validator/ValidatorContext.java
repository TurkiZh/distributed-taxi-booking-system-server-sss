package com.robertnorthard.dtbs.server.layer.business.rules.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements validator result specification.
 * 
 * @author robertnorthard
 */
public class ValidatorContext implements ValidatorResult {

    private final List<String> errors = new ArrayList<>();
    
   /**
     * Add error message.
     * 
     * @param error error message to add.
     */
    @Override
    public void addError(String error) {
        this.errors.add(error);
    }

   /**
     * Return list of errors.
     * 
     * @return list of errors.
     */
    @Override
    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Return true if failed business rules, else false.
     * 
     * @return true if failed business rules, else false. 
     */
    @Override
    public boolean isFailedBusinessRules() {
        return errors.isEmpty();
    }    
}

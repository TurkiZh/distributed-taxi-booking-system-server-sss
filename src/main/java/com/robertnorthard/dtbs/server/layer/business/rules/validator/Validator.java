package com.robertnorthard.dtbs.server.layer.business.rules.validator;

/**
 * Entity validator interface.
 * 
 * @author robertnorthard
 */
public abstract class Validator<T> {
    
    private ValidatorResult result;
    
    /**
     * Default constructor.
     * 
     * @param result placeholder for storing validator results. 
     */
    public Validator(ValidatorResult result){
        this.result = result;
    }
    
    /**
     * Valid provided entity by realising appropriate business specification rules.
     * @param entity entity to validate.
     * @return true if validated successfully  else false.
     */
    public abstract boolean validate(T entity);
    
    /**
     * @return instance of validator result.
     */
    public ValidatorResult getValidatorResult(){
        return this.result;
    }
}

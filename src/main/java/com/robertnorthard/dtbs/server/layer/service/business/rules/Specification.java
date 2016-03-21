package com.robertnorthard.dtbs.server.layer.service.business.rules;

import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;

/**
 * Business rule specification interface.
 * 
 * @param <T> entity type to validate.
 * @author robertnorthard
 */
public interface Specification<T> {
    
    /**
     * Return true if conditions satisfied else false.
     * 
     * @param candidate entity to validate.
     * @return true if condition satisfied else false.
     */
    public boolean isSatisfiedBy(T candidate);
    
    /**
     * Chain to conditions by the logical AND operator.
     * 
     * @param other other specification to chain.
     * @return true if both specification are true else false.
     */
    public Specification<T> and(Specification<T> other);
    
    /**
     * Return error results from failed business rules.
     * 
     * @return error results from failed business rules.
     */
    public ValidatorResult getErrorResult();
}

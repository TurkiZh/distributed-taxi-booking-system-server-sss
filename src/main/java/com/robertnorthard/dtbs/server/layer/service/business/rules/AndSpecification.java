package com.robertnorthard.dtbs.server.layer.service.business.rules;

import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;

/**
 * Implements logical AND specification.
 * 
 * @author robertnorthard
 */
public class AndSpecification<T> extends CompositeSpecification<T> {

    private final Specification<T> left;
    private final Specification<T> right;
    
    public AndSpecification(ValidatorResult result, Specification<T> left, Specification<T> right){
        super(result);
        this.left = left;
        this.right = right;
    }
    
    @Override
    public boolean isSatisfiedBy(T candidate) {
        return this.left.isSatisfiedBy(candidate) && this.right.isSatisfiedBy(candidate);
    }
    
}

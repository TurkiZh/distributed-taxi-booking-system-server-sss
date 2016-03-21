package com.robertnorthard.dtbs.server.layer.service.business.rules;

import com.robertnorthard.dtbs.server.layer.service.business.rules.validator.ValidatorResult;

/**
 * Composite specification class.
 * Facilitates chaining of specifications to implements complex business rules.
 * 
 * @author robertnorthard
 */
public abstract class CompositeSpecification<T> implements Specification<T>  {

    private ValidatorResult result;
    
    public CompositeSpecification(ValidatorResult result){
        this.result = result;
    }
    
    @Override
    public abstract boolean isSatisfiedBy(T candidate);

    @Override
    public Specification<T> and(Specification<T> other) {
        return new AndSpecification<>(result, this, other);
    }
    
    @Override
    public ValidatorResult getErrorResult() {
        return this.result;
    }
}

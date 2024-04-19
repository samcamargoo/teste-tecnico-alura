package com.sam.alura.shared;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;
import java.util.List;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {

    private String domainAttribute;
    private Class<?> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueField params) {
        domainAttribute = params.fieldName();
        clazz = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery("SELECT 1 FROM " + clazz.getName() + " WHERE " + domainAttribute + "=:value");
        query.setParameter("value", value);
        List<?> result = query.getResultList();
        Assert.state(result.size() < 2, "Found more than one " + clazz + " with the attribute " + domainAttribute + "= " + value);
        return result.isEmpty();
    }
}

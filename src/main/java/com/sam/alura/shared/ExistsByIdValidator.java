package com.sam.alura.shared;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;

public class ExistsByIdValidator implements ConstraintValidator<ExistsById, Object> {

    private Class<?> clazz;
    private String domainAttribute;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(ExistsById params) {
        this.domainAttribute = params.fieldName();
            this.clazz = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Query query = entityManager.createQuery("SELECT 1 FROM " + clazz.getName() + " WHERE " + domainAttribute + "=:value");
        query.setParameter("value", value);

        List<?> list = query.getResultList();
        Assert.isTrue(list.size() <= 1, "There is more than one class " + clazz + " with attribute " + domainAttribute + " with value = " + value);

        return !list.isEmpty();
    }
}

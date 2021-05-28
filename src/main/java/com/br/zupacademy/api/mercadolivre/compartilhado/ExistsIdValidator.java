package com.br.zupacademy.api.mercadolivre.compartilhado;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {

	@Autowired
	private EntityManager manager;
	private Class<?> domainClass;
	private String fieldName;
	
	@Override
	public void initialize(ExistsId params) {
		this.domainClass = params.domainClass();
		this.fieldName = params.fieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query = manager.createQuery("select c from " + domainClass.getName() + " c where c."+ fieldName +" = :value");
		query.setParameter("value", value);
		
		List<?> resultList = query.getResultList();
		return !resultList.isEmpty();
	}

}

package com.br.zupacademy.api.mercadolivre.compartilhado;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ExistsIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsId {
	String message() default "Esse registro não existe.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	Class<?> domainClass();
	
	String fieldName();
}

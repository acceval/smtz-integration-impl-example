package com.acceval.core.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MicroServiceField {

	/** optional, can define in MicroServiceObject.serviceID */
	String serviceID() default "";

	/** optional, can define in MicroServiceObject.function */
	String function() default "";

	String sortFunction() default "";

	String mockTarget();
}

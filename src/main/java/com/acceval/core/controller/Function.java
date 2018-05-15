package com.acceval.core.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * assign this annotation to let Identity MS auto detect this method for Access Control 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Function {
	String clientAccess() default "";

	String description() default "";
}

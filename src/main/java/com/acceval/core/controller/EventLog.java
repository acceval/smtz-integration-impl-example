package com.acceval.core.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.acceval.core.repository.PersistentEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventLog {

	public enum EventAction implements PersistentEnum {
		// @formatter:off
		READ(0), 
		CREATE(1), 
		DELETE(2), 
		UPDATE(3),
		APPROVE(4),
		REJECT(5),
		UPLOAD(6),
		DOWNLOAD(7),
		SEARCH(8),
		LOGIN(9),
		FORGET_PASSWORD(10),
		OTHER(99);

		private final int id;
		private EventAction(int id) { this.id = id; }
		@Override
		public int getID() { return this.id; }
		// @formatter:on

	}

	EventAction eventAction() default EventAction.OTHER;

}

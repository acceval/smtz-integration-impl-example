package com.acceval.core.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MicroServiceObject {

	public static final String SRC_MASTERDATA = "masterdata-service";
	public static final String SRC_INDUSTRY_ANALYSIS = "industry-analysis-service";
	public static final String SRC_IDENTITY = "identity-service";
	public static final String SRC_PRICING = "pricing-service";
	public static final String SRC_ETL = "etl-service";
	public static final String SRC_PRICING_POWER = "pricing-power-service";

	public static final String COMMON_GT_OBJ = "getObj";
	public static final String COMMON_QUERY = "common/query";

	/** eg. masterdata-service, can deriave from originEntityClass */
	//	String serviceID() default "";

	/** use for /{module}/getObj/{id} */
	String module() default "";

	String primaryKey() default "id";

	String originEntityClass() default "";
	
	/** will trigger /common/query/ instead of fire module/getObj/ */
	boolean useCommonQuery() default false;

}

package com.acceval.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.RequestTemplate;

public class RequestInterceptorUtil {
	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptorUtil.class);

	public static void apply(RequestTemplate template) {

		String token = PrincipalUtil.getToken();

		if (token != null) {

//			template.header("Authorization", token);

			CurrentUser user = PrincipalUtil.getCurrentUser();

			template.header(PrincipalUtil.HDRKEY_COMPANYID, String.valueOf(user.getCompanyId()));
			template.header(PrincipalUtil.HDRKEY_COMPANYCODE, user.getCompanyCode());
			template.header(PrincipalUtil.HDRKEY_SCHEMANAME, user.getSchemaName());
			template.header(PrincipalUtil.HDRKEY_TIMEZONEID, user.getTimeZone());
			if (user.getServicePackage() != null) {
				template.header(PrincipalUtil.HDRKEY_SERVICEPACKAGE, user.getServicePackage().toString());
			}

		} else {
			CurrentUser user = PrincipalUtil.getSystemUser();

			if (user != null) {
				template.header(PrincipalUtil.HDRKEY_COMPANYID, String.valueOf(user.getCompanyId()));
				template.header(PrincipalUtil.HDRKEY_COMPANYCODE, user.getCompanyCode());
				template.header(PrincipalUtil.HDRKEY_SCHEMANAME, user.getSchemaName());
				template.header(PrincipalUtil.HDRKEY_TIMEZONEID, user.getTimeZone());
				if (user.getServicePackage() != null) {
					template.header(PrincipalUtil.HDRKEY_SERVICEPACKAGE, user.getServicePackage().toString());
				}
			} else {
				template.header(PrincipalUtil.HDRKEY_COMPANYID, "");
				template.header(PrincipalUtil.HDRKEY_COMPANYCODE, "");
				template.header(PrincipalUtil.HDRKEY_SCHEMANAME, "");
				template.header(PrincipalUtil.HDRKEY_TIMEZONEID, "");
			}
		}
	}

}

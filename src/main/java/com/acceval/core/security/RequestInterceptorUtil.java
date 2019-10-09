package com.acceval.core.security;

import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestInterceptorUtil {
	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptorUtil.class);

	public static void apply(RequestTemplate template) {
		
		String token = PrincipalUtil.getToken();
		
		if (token != null) {

//			template.header("Authorization", token);

			CurrentUser user = PrincipalUtil.getCurrentUser();

			template.header(PrincipalUtil.HDRKEY_COMPANYID, String.valueOf(user.getCompanyId()));
			template.header(PrincipalUtil.HDRKEY_COMPANYCODE, user.getCompanyCode());
			if (user.getServicePackage() != null) {
				template.header(PrincipalUtil.HDRKEY_SERVICEPACKAGE, user.getServicePackage().toString());
			}

		} else {
			CurrentUser user = PrincipalUtil.getSystemUser();

			if (user != null) {
				template.header(PrincipalUtil.HDRKEY_COMPANYID, String.valueOf(user.getCompanyId()));
				template.header(PrincipalUtil.HDRKEY_COMPANYCODE, user.getCompanyCode());
				if (user.getServicePackage() != null) {
					template.header(PrincipalUtil.HDRKEY_SERVICEPACKAGE, user.getServicePackage().toString());
				}
			} else {
				template.header(PrincipalUtil.HDRKEY_COMPANYID, "");
				template.header(PrincipalUtil.HDRKEY_COMPANYCODE, "");
			}
		}
	}

}

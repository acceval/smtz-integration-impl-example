package com.acceval.core.security;

import feign.RequestTemplate;

public class RequestInterceptorUtil {

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

		}
	}

}

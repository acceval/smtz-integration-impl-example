package com.acceval.core.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.acceval.core.model.ServicePackage;

public class PrincipalUtil {

	public static final String HDRKEY_COMPANYID = "COMPANYID";
	public static final String HDRKEY_COMPANYCODE = "COMPANYCODE";
	public static final String HDRKEY_SERVICEPACKAGE = "SERVICEPACKAGE";

	public static void setSystemUser(Long companyID, String companyCode, String servicePackage) {
		CurrentUser sysUser = getCurrentUser();
		sysUser.setSystemUser(true);
		sysUser.setCompanyId(companyID);
		sysUser.setCompanyCode(companyCode);
		if (servicePackage != null) {
			sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
		}
	}

	public static CurrentUser getCurrentUser() {
		
		CurrentUser tokenUser = getUserFromToken();

		if ((tokenUser == null || tokenUser.getCompanyId() == null) 
				&&  RequestContextHolder.getRequestAttributes() != null) {
			// system user
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String companyID = request.getHeader(PrincipalUtil.HDRKEY_COMPANYID);
			String companyCode = request.getHeader(PrincipalUtil.HDRKEY_COMPANYCODE);
			String servicePackage = request.getHeader(PrincipalUtil.HDRKEY_SERVICEPACKAGE);
			//			System.out.println("principal " + request.getRequestURI());
			if (StringUtils.isNotBlank(companyID)) {
				if (tokenUser == null) {
					CurrentUser sysUser = new CurrentUser();
					sysUser.setSystemUser(true);
					sysUser.setCompanyId(Long.valueOf(companyID));
					sysUser.setCompanyCode(companyCode);
					if (servicePackage != null) {
						sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
					}
					return sysUser;
				} else {
					tokenUser.setCompanyId(Long.valueOf(companyID));
					tokenUser.setSystemUser(true);
					if (StringUtils.isNotBlank(companyCode)) {
						tokenUser.setCompanyCode(companyCode);
					}
					if (StringUtils.isNotBlank(servicePackage)) {
						tokenUser.setServicePackage(ServicePackage.valueOf(servicePackage));
					}
				}
			}
		}

		return tokenUser;
	}

	public static String getToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) return null;

		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		String token = details.getTokenType() + " " + details.getTokenValue();
		return token;
	}

	public static CurrentUser getUserFromToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof CurrentUser) {
				return (CurrentUser) principal;
			}
		}
		return null;
	}

	public static String getSchemaName() {
		String schemaName = "shared";
		CurrentUser user = getCurrentUser();
		if (user != null && user.getCompanyCode() != null) {
			schemaName = user.getCompanyCode().toLowerCase();
		}
		return schemaName;
	}

	public static Long getCompanyID() {
		CurrentUser user = getCurrentUser();
		if (user != null) {
			return user.getCompanyId();
		}
		return null;
	}

	public static String getUsername() {
		CurrentUser user = getCurrentUser();
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	public static String getCompanyCode() {
		CurrentUser user = getCurrentUser();
		if (user != null) {
			return user.getCompanyCode();
		}
		return null;
	}

	public static ServicePackage getServicePackage() {
		ServicePackage sp = ServicePackage.TIER_ONE;
		CurrentUser user = getCurrentUser();
		if (user != null && user.getServicePackage() != null) {
			sp = user.getServicePackage();
		}
		return sp;
	}

}

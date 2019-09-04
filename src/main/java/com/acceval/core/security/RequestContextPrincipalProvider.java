package com.acceval.core.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.acceval.core.model.ServicePackage;

public class RequestContextPrincipalProvider implements PrincipalProvider {
	
	@Override
	public CurrentUser currentUser() {
		
		CurrentUser tokenUser = getUserFromToken();

		if ((tokenUser == null || tokenUser.getCompanyId() == null)
				&& RequestContextHolder.getRequestAttributes() != null) {
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String companyID = request.getHeader(PrincipalUtil.HDRKEY_COMPANYID);
			companyID = "null".equalsIgnoreCase(companyID) ? null : companyID;
			String companyCode = request.getHeader(PrincipalUtil.HDRKEY_COMPANYCODE);
			String servicePackage = request.getHeader(PrincipalUtil.HDRKEY_SERVICEPACKAGE);
			String schemaName = request.getHeader(PrincipalUtil.HDRKEY_SCHEMANAME);
						
			if (StringUtils.isNotBlank(companyID)) {
				if (tokenUser == null) {
					CurrentUser sysUser = new CurrentUser();
					sysUser.setCompanyId(Long.valueOf(companyID));
					sysUser.setCompanyCode(companyCode);
					sysUser.setSchemaName(schemaName);
					if (servicePackage != null) {
						sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
					}
					return sysUser;
				} else {
					tokenUser.setCompanyId(Long.valueOf(companyID));
					tokenUser.setSchemaName(schemaName);
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

	private CurrentUser getUserFromToken() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof CurrentUser) {
				return (CurrentUser) principal;
			}
		}
		return null;
	}

	@Override
	public String getToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) return null;

		if (auth.getDetails() instanceof OAuth2AuthenticationDetails) {
			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
			String token = details.getTokenType() + " " + details.getTokenValue();
			return token;
		}

		return null;
	}
}

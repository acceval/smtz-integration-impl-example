package com.acceval.core.security;

import com.acceval.core.model.ServicePackage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.validation.constraints.NotNull;

public class PrincipalUtil {
	private static final ThreadLocal<PrincipalProvider> provider = new ThreadLocal<>();

	public static final String HDRKEY_COMPANYID = "COMPANYID";
	public static final String HDRKEY_COMPANYCODE = "COMPANYCODE";
	public static final String HDRKEY_SERVICEPACKAGE = "SERVICEPACKAGE";
	public static final String HDRKEY_SCHEMANAME = "SCHEMANAME";

	public static void setProvider(PrincipalProvider provider) {
		if (PrincipalUtil.provider.get() != null) {
			throw new IllegalStateException("a principal provider already exists, unable to set provider again");
		}

		PrincipalUtil.provider.set(provider);
	}

	public static void removeProvider() {
		PrincipalUtil.provider.remove();
	}

	@NotNull
	private static PrincipalProvider getProvider() {
		PrincipalProvider provider = PrincipalUtil.provider.get();

		if (provider == null) {
			return new PrincipalProvider() {
				@Override
				public CurrentUser currentUser() {
					return null;
				}

				@Override
				public String getToken() {
					return null;
				}	
			};
		}

		return provider;
	}

	public static CurrentUser getCurrentUser() {
		return getProvider().currentUser();
	}

	public static String getToken() {
		return getProvider().getToken();
	}

	public static void setSystemUser(Long companyID, String companyCode, String servicePackage) {
		
		CurrentUser sysUser = getCurrentUser();
		sysUser.setSystemUser(true);
		sysUser.setCompanyId(companyID);
		sysUser.setCompanyCode(companyCode);		
		if (servicePackage != null) {
			sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
		}
	}

	public static String getSchemaName() {
		String schemaName = "shared";
		CurrentUser user = getCurrentUser();
		if (user != null && user.getSchemaName() != null) {
			schemaName = user.getSchemaName().toLowerCase();
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

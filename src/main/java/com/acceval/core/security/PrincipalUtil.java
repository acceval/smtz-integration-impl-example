package com.acceval.core.security;

import javax.validation.constraints.NotNull;

import com.acceval.core.model.ServicePackage;

public class PrincipalUtil {
	
	private static final ThreadLocal<PrincipalProvider> provider = new ThreadLocal<>();
	private static final ThreadLocal<String> auditLogUUID = new ThreadLocal<>();

	public static final String HDRKEY_COMPANYID = "COMPANYID";
	public static final String HDRKEY_COMPANYCODE = "COMPANYCODE";
	public static final String HDRKEY_SERVICEPACKAGE = "SERVICEPACKAGE";
	public static final String HDRKEY_SCHEMANAME = "SCHEMANAME";

	public static void setProvider(PrincipalProvider provider) {
		
		if (PrincipalUtil.provider.get() != null) {
			return;
//			throw new IllegalStateException("a principal provider already exists, unable to set provider again");
		}
		PrincipalUtil.provider.set(provider);
	}

	public static void setProvider(PrincipalProvider provider, String auditLogUUID) {
		PrincipalUtil.auditLogUUID.set(auditLogUUID);
		PrincipalUtil.setProvider(provider);
	}

	public static String getAuditLogUUID() {
		return PrincipalUtil.auditLogUUID.get();
	}

	public static void removeProvider() {
		PrincipalUtil.provider.remove();
		PrincipalUtil.auditLogUUID.remove();
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
//		sysUser.setSystemUser(true);
		sysUser.setCompanyId(companyID);
		sysUser.setCompanyCode(companyCode);		
		if (servicePackage != null) {
			sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
		}
	}

	public static String getSchemaName() {
		String schemaName = "public";
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

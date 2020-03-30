package com.acceval.core.security;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acceval.core.model.ServicePackage;
import com.acceval.core.model.Timezone;

public class PrincipalUtil {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final ThreadLocal<PrincipalProvider> provider = new ThreadLocal<>();
	private static final ThreadLocal<CurrentUser> systemUser = new ThreadLocal<>();
	private static final ThreadLocal<String> auditLogUUID = new ThreadLocal<>();

	public static final String HDRKEY_COMPANYID = "COMPANYID";
	public static final String HDRKEY_COMPANYCODE = "COMPANYCODE";
	public static final String HDRKEY_SERVICEPACKAGE = "SERVICEPACKAGE";
	public static final String HDRKEY_SCHEMANAME = "SCHEMANAME";
	public static final String HDRKEY_TIMEZONEID = "TIMEZONEID";

	public static final String USERNAME_GUEST = "GUEST";

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

	public static void setSystemUser(Long companyID, String companyCode, Timezone timezone, String servicePackage) {

		CurrentUser sysUser = getCurrentUser();
		if (sysUser == null)
			sysUser = new CurrentUser();
		sysUser.setCompanyId(companyID);
		sysUser.setCompanyCode(companyCode);
		sysUser.setSchemaName(companyCode);
		if (timezone != null) {
		sysUser.setTimeZone(timezone.getUtcId());
		sysUser.setTimeZoneName(timezone.getText());
		}
		if (servicePackage != null) {
			sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
		}
		PrincipalUtil.systemUser.set(sysUser);
	}

	public static void setSystemUser(Long companyID, String companyCode, String timezone, String servicePackage) {

		CurrentUser sysUser = getCurrentUser();
		if (sysUser == null)
			sysUser = new CurrentUser();
//		sysUser.setSystemUser(true);
		sysUser.setCompanyId(companyID);
		sysUser.setCompanyCode(companyCode);
		sysUser.setSchemaName(companyCode);
		sysUser.setTimeZone(timezone);
		if (servicePackage != null) {
			sysUser.setServicePackage(ServicePackage.valueOf(servicePackage));
		}
		PrincipalUtil.systemUser.set(sysUser);
	}

	public static CurrentUser getSystemUser() {
		return systemUser.get();
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

	public static String getTimeZone() {
		CurrentUser user = getCurrentUser();
		if (user != null) {
			return user.getTimeZone();
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

	public static boolean isGuest() {
		return USERNAME_GUEST.equalsIgnoreCase(getUsername());
	}
}

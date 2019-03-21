package com.acceval.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.acceval.core.model.ServicePackage;

public class SessionUtil {

	public static String getToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) return null;

		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		String token = details.getTokenType() + " " + details.getTokenValue();
		return token;
	}

	public static String getSchemaName() {

		String schemaName = "shared";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof CurrentUser) {

				CurrentUser currentUser = (CurrentUser) principal;

				if (currentUser.getCompanyCode() != null) {

					schemaName = currentUser.getCompanyCode().toLowerCase();
				}
			}
		}
		return schemaName;
	}

	public static Long getCompanyId() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof CurrentUser) {

				CurrentUser currentUser = (CurrentUser) principal;
				return currentUser.getCompanyId();
			}
		}
		return null;
	}

	public static String getUsername() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof CurrentUser) {

				CurrentUser currentUser = (CurrentUser) principal;
				return currentUser.getUsername();
			}
		}
		return null;
	}

	public static String getCompanyCode() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof CurrentUser) {

				CurrentUser currentUser = (CurrentUser) principal;
				return currentUser.getCompanyCode();
			}
		}
		return null;
	}

	public static ServicePackage getServicePackage() {

		ServicePackage sp = ServicePackage.TIER_ONE;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof CurrentUser) {

				CurrentUser currentUser = (CurrentUser) principal;
				if (currentUser.getServicePackage() != null) {
					sp = currentUser.getServicePackage();
				}
			}
		}
		return sp;
	}

}

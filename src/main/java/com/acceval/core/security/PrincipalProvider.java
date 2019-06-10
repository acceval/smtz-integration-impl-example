package com.acceval.core.security;

public interface PrincipalProvider {
	CurrentUser currentUser();

	String getToken();
}

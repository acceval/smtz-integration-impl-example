package com.acceval.core.model;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof AuthUser) {

				AuthUser authUser = (AuthUser) principal;
				return Optional.of(authUser.getUsername());
			}
		}
		return Optional.of("anonymousUser");
	}
}

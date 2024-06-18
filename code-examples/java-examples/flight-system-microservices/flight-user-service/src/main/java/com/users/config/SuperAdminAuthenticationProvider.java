package com.users.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;


// Super Admin can skip authentication using password
public class SuperAdminAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();

        if ("SweProfile".equals(username)) {
            return UsernamePasswordAuthenticationToken.authenticated(
                    "SweProfile",
                    null,
                    AuthorityUtils.createAuthorityList("SUPER_role")
            );
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

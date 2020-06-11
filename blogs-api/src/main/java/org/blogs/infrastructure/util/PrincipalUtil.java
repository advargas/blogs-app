package org.blogs.infrastructure.util;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

public class PrincipalUtil {

    public static String getUsername(Principal principal) {
        if (principal != null) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
            return token.getTokenAttributes().get("preferred_username").toString();
        }
        return null;
    }
}

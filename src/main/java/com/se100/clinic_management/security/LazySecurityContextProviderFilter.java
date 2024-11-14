package com.se100.clinic_management.security;


import com.se100.clinic_management.utils.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class LazySecurityContextProviderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        var context = SecurityContextHolder.getContext();
        SecurityContextHolder.setContext(new LazyJwtSecurityContextProvider(req, res, context));
        filterChain.doFilter(req, res);
    }

    @RequiredArgsConstructor
    static class LazyJwtSecurityContextProvider implements SecurityContext {

        private final HttpServletRequest req;
        private final HttpServletResponse res;
        private final SecurityContext securityCtx;


        @SneakyThrows
        @Override
        public Authentication getAuthentication() {
            if (securityCtx.getAuthentication() == null || securityCtx.getAuthentication() instanceof AnonymousAuthenticationToken) {
                try {
                    var jwtToken = SecurityUtil.getToken(this.req);
                    var decodedJWT = SecurityUtil.validate(jwtToken);

                    if (decodedJWT.getExpiresAt().before(new Date())) {
                        //throw new BaseError("TOKEN_EXPIRED", "Token expired", HttpStatus.UNAUTHORIZED);
                        throw new AuthenticationServiceException("Token expired.");
                    }

                    var jwtTokenVo = SecurityUtil.getValueObject(decodedJWT);
                    var authToken = new PreAuthenticatedAuthenticationToken(jwtTokenVo, null, jwtTokenVo.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    securityCtx.setAuthentication(authToken);
                } catch (Exception e) {
                    log.debug("Can't get authentication context: " + e.getMessage());
                }

            }

            return securityCtx.getAuthentication();
        }


        @Override
        public void setAuthentication(Authentication authentication) {
            securityCtx.setAuthentication(authentication);
        }
    }


}


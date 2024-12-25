package com.se100.clinic_management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se100.clinic_management.dto.JwtTokenVo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private static final int SIX_HOURS_MILLISECOND = 1000 * 60 * 60 * 6;
    private static final int SIX_HOURS = 3600 * 6;
    private static final int SEVEN_DAYS = 1000 * 60 * 60 * 24 * 7;
    private static final int FIFTEEN_MINUTES = 1000 * 60 * 15;
    private static final int THIRTY_MINUTES = 1000 * 60 * 30;
    private static final int FIVE_MINUTES = 1000 * 60 * 5;

    private static final String USER_CLAIM = "user";
    private static final String ISSUER = "auth0";

    // private static String SECRET_KEY = System.getenv("LOGIN_JWT_KEY");
    private static final String SECRET_KEY = "clinic_management";

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    @SneakyThrows
    public static String createRefreshToken(JwtTokenVo jwtTokenVo) {
        var builder = JWT.create();
        String tokenJson;
        try {
            tokenJson = OBJECT_MAPPER.writeValueAsString(jwtTokenVo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        builder.withClaim(USER_CLAIM, tokenJson);
        // Thời gian hết hạn của Refresh Token dài hơn Access Token
        return builder
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + SEVEN_DAYS)) // 7 ngày
                .sign(ALGORITHM);
    }

    @SneakyThrows
    public static String createToken(JwtTokenVo jwtTokenVo) {
        var builder = JWT.create();
        String tokenJson = null;
        try {
            tokenJson = OBJECT_MAPPER.writeValueAsString(jwtTokenVo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        builder.withClaim(USER_CLAIM, tokenJson);
        return builder
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + SEVEN_DAYS)) // 7 ngày
                .sign(ALGORITHM);
    }

    public static void setJwtToClient(JwtTokenVo vo) {
        var accessToken = createToken(vo);
        var refreshToken = createRefreshToken(vo);

        var accessCookie = new Cookie(AUTHORIZATION_HEADER, AUTHORIZATION_PREFIX + accessToken);
        accessCookie.setMaxAge(SIX_HOURS);
        accessCookie.setPath("/");

        var refreshCookie = new Cookie("RefreshToken", refreshToken);
        refreshCookie.setMaxAge(1000 * 60 * 60 * 24 * 7); // 7 ngày
        refreshCookie.setPath("/");

        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attributes.getResponse().addCookie(accessCookie);
        attributes.getResponse().addCookie(refreshCookie);
    }

    @SneakyThrows
    public static DecodedJWT validate(String token) {
        var verifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build();
        return verifier.verify(token);
    }

    @SneakyThrows
    public static JwtTokenVo getValueObject(DecodedJWT decodedJWT) {
        Map<String, Claim> claim = decodedJWT.getClaims();

        var claimUser = claim.get(USER_CLAIM);

        var userClaim = decodedJWT.getClaims().get(USER_CLAIM).asString();
        try {
            return OBJECT_MAPPER.readValue(userClaim, JwtTokenVo.class);
        } catch (JsonProcessingException e) {
            // log error
            System.out.println("Error: " + e.getMessage());

            throw new RuntimeException(e);
        }
    }

    public static String getToken(HttpServletRequest req) throws AccessDeniedException {
        var token = req.getHeader(AUTHORIZATION_HEADER);
        // Check if token is null
        if (token == null) {
            throw new AccessDeniedException("Not authorized.");
        }
        // Check if token is not start with Bearer
        if (!token.startsWith(AUTHORIZATION_PREFIX)) {
            throw new AccessDeniedException("Not authorized.");
        }

        String jwtToken = token.substring(AUTHORIZATION_PREFIX.length());
        return jwtToken;
    }

    public static JwtTokenVo getSession() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Not authorized.");
        }
        return (JwtTokenVo) authentication.getPrincipal();
    }
}

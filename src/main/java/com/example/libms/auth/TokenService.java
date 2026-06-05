package com.example.libms.auth;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    // Access token: short-lived (15 min)
    private static final Duration ACCESS_TOKEN_EXPIRY  = Duration.ofMinutes(15);
    // Refresh token: long-lived (7 days)
    private static final Duration REFRESH_TOKEN_EXPIRY = Duration.ofDays(7);

    public String generateAccessToken(UserDetails user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("libms")
                .subject(user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TOKEN_EXPIRY))
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .build();

        return encoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(UserDetails user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("libms")
                .subject(user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_TOKEN_EXPIRY))
                .claim("type", "refresh")
                .build();

        return encoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String extractSubject(String token) {
        try {
            JwtDecoder decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
            Jwt jwt = decoder.decode(token);

            // make sure it's actually a refresh token
            String type = jwt.getClaimAsString("type");
            if (!"refresh".equals(type))
                throw new BadCredentialsException("Not a refresh token");

            return jwt.getSubject();
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }
    }

    private JwtEncoder encoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }
}

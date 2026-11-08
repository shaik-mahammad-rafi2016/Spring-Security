package com.security.Jwt.Implementation.Project.security.service;
import java.time.Instant;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {

        Instant now = Instant.now();

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("jwt-security-demo")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256)
                .type("JWT")
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims)
        ).getTokenValue();
    }
}

package com.security.Jwt.Implementation.Project.security.config;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.interfaces.RSAPublicKey;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtKeyConfig {

    @Bean
    public KeyPair jwtKeyPair() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        return keyPairGenerator.generateKeyPair();
    }
    @Bean
    public JwtEncoder jwtEncoder(KeyPair keyPair) {

        RSAKey rsaKey = new RSAKey.Builder(
                (java.security.interfaces.RSAPublicKey) keyPair.getPublic()
        )
                .privateKey(keyPair.getPrivate())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        ImmutableJWKSet<com.nimbusds.jose.proc.SecurityContext> jwkSource =
                new ImmutableJWKSet<>(jwkSet);

        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair) {

        RSAPublicKey publicKey =
                (RSAPublicKey) keyPair.getPublic();

        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}
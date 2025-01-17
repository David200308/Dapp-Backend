package com.skyproton.backend.tools;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.jose4j.jwt.JwtClaims;

public class Auth {

    public String createJwt(
            String subject,
            String audience,
            String authUuid
    ) throws JoseException {
        // For docker secret
        final String JWT_ISSUER = EnvLoad.GetEnv("JWT_ISSUER_FILE");
        final String JWT_SECRET_KEY = EnvLoad.GetEnv("JWT_SECRET_KEY_FILE");

        // For local dotenv file
        // final String JWT_ISSUER = EnvLoad.GetEnv("JWT_ISSUER");
        // final String JWT_SECRET_KEY = EnvLoad.GetEnv("JWT_SECRET_KEY");

        JwtClaims claims = new JwtClaims();
        claims.setSubject(subject);
        claims.setAudience(audience);
        claims.setIssuer(JWT_ISSUER);
        claims.setClaim("authUuid", authUuid);
        claims.setExpirationTimeMinutesInTheFuture(60);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(new HmacKey(JWT_SECRET_KEY.getBytes()));

        return jws.getCompactSerialization();
    }

    public JwtClaims verifyJwt(
            String subject,
            String token
    ) throws Exception {
        // For docker secret
        final String JWT_ISSUER = EnvLoad.GetEnv("JWT_ISSUER_FILE");
        final String JWT_SECRET_KEY = EnvLoad.GetEnv("JWT_SECRET_KEY_FILE");

        // For local dotenv file
        // final String JWT_ISSUER = EnvLoad.GetEnv("JWT_ISSUER");
        // final String JWT_SECRET_KEY = EnvLoad.GetEnv("JWT_SECRET_KEY");

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setExpectedIssuer(JWT_ISSUER)
                .setExpectedSubject(subject)
                .setVerificationKey(new HmacKey(JWT_SECRET_KEY.getBytes()))
                .build();

        return jwtConsumer.processToClaims(token);
    }

}

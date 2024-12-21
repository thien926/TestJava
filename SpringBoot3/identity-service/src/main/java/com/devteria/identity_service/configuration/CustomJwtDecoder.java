package com.devteria.identity_service.configuration;

import com.devteria.identity_service.dto.request.IntrospectRequest;
import com.devteria.identity_service.dto.response.IntrospectResponse;
import com.devteria.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Autowired
    @Lazy
    private AuthenticationService authenticationService;

    @Override
    public Jwt decode(String token) throws JwtException {
        // Kiểm tra tính hợp lệ của signerKey
        if (signerKey == null || signerKey.isEmpty()) {
            throw new IllegalStateException("JWT signer key must be provided!");
        }

        try {
            IntrospectResponse introspectResponse = authenticationService.introspect(IntrospectRequest.builder().token(token).build());
            if(!introspectResponse.getValid()) {
                throw new JwtException("Token invalid");
            }
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if(Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), String.valueOf(MacAlgorithm.HS512));

            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}

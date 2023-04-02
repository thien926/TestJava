package com.example.movie.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.movie.service.JwtService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtServiceImpl implements JwtService {
	@Autowired
	private Environment env;
	public static final String USERNAME = "username";

	public String generateTokenLogin(String username) {
		String token = null;
		try {
			// Create IMPAC signer
			JWSSigner signer = new MACSigner(generateShareSecret());

			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(USERNAME, username);
//			builder.claim("Name", "test");
			builder.expirationTime(generateExpirationDate());

			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			// Apply the HMAC protection
			signedJWT.sign(signer);
			// Serialize to compact form, produces something like
			token = signedJWT.serialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}

	private Date generateExpirationDate() {
		int expire_time = Integer.parseInt(env.getProperty("expire_time"));
		return new Date(System.currentTimeMillis() + expire_time);
	}

	private Date getExpirationDateFromToken(String token) {
		Date expirationDate = null;
		JWTClaimsSet claims = getClaimsFromToken(token);
		expirationDate = claims.getExpirationTime();
		return expirationDate;
	}

	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			username = claims.getStringClaim(USERNAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}

	private byte[] generateShareSecret() {
		byte[] sharedSecret = new byte[32];
		String secret_key = env.getProperty("secret_key");
		sharedSecret = secret_key.getBytes();
		return sharedSecret;
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateTokenLogin(String token) {
		if (token == null || token.trim().length() <= 0) {
			return false;
		}
		String username = getUsernameFromToken(token);

		return !(username == null || username.isEmpty() || isTokenExpired(token));
	}
}

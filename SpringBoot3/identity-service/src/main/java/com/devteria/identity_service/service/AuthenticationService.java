package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.AuthenticationRequest;
import com.devteria.identity_service.dto.request.IntrospectRequest;
import com.devteria.identity_service.dto.request.LogoutRequest;
import com.devteria.identity_service.dto.request.RefreshRequest;
import com.devteria.identity_service.dto.response.AuthenticationResponse;
import com.devteria.identity_service.dto.response.IntrospectResponse;
import com.devteria.identity_service.entity.InvalidatedToken;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.repository.InvalidatedTokenRepository;
import com.devteria.identity_service.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    /**
     * Xác thực token thông qua introspect.
     *
     * @param request Yêu cầu introspect chứa token cần xác thực
     * @return Kết quả xác thực với thông tin hợp lệ
     * @throws ParseException Nếu token không thể phân tích cú pháp
     * @throws JOSEException Nếu lỗi xảy ra trong quá trình xác thực token
     */
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), false);
        return IntrospectResponse.builder()
                .valid(signedJWT != null)
                .build();
    }

    /**
     * Xác thực thông tin đăng nhập và tạo JWT token.
     *
     * @param authenticationRequest Yêu cầu xác thực với thông tin username và password
     * @return Kết quả xác thực và token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        // Tìm người dùng theo username
        User user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra mật khẩu
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Tạo token JWT
        String token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(authenticated).build();
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);
        if(signedJWT == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiredTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        String token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);
        if(signedJWT == null) {
            log.info("Token [{}] already expired", request.getToken());
            return;
        }
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jit, expiredTime);
        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        // Tạo verifier từ khóa ký
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        // Phân tích token
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiredTime = isRefresh
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        // Xác thực token và kiểm tra thời hạn
        boolean isValid = signedJWT.verify(verifier) && expiredTime.after(new Date());
        if(isValid) {
            isValid = !invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());
        }
        if(isValid) {
            return signedJWT;
        }
        return null;
    }

    /**
     * Tạo JWT token cho người dùng.
     *
     * @param user Đối tượng người dùng
     * @return Chuỗi token đã ký
     */
    private String generateToken(User user) {
        // Tạo header JWT với thuật toán HS512
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // Tạo claims với thông tin người dùng
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("devteria.com") // Định danh nhà phát hành token
                .issueTime(new Date()) // Thời gian phát hành
                .expirationTime(new Date(new Date().getTime() + 1000 * VALID_DURATION)) // Thời gian hết hạn: VALID_DURATION s
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user)) // Thêm các quyền của người dùng
                .claim("userId", user.getId()) // Thêm Id của user
                .build();

        // Tạo payload và ký token
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            // Trả về token đã ký
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Tạo danh sách quyền (scope) cho người dùng.
     *
     * @param user Đối tượng người dùng
     * @return Chuỗi scope phân cách bởi khoảng trắng
     */
    private String buildScope(User user) {
        // Sử dụng StringJoiner để xây dựng danh sách quyền
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                String roleName = role.getName();
                stringJoiner.add("ROLE_" + roleName);
                if(!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(roleName + "_" + permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}

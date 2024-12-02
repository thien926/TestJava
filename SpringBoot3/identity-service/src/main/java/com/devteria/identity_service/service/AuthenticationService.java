package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.AuthenticationRequest;
import com.devteria.identity_service.dto.request.IntrospectRequest;
import com.devteria.identity_service.dto.response.AuthenticationResponse;
import com.devteria.identity_service.dto.response.IntrospectResponse;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.exception.ErrorCode;
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
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Xác thực token thông qua introspect.
     *
     * @param request Yêu cầu introspect chứa token cần xác thực
     * @return Kết quả xác thực với thông tin hợp lệ
     * @throws ParseException Nếu token không thể phân tích cú pháp
     * @throws JOSEException Nếu lỗi xảy ra trong quá trình xác thực token
     */
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        // Tạo verifier từ khóa ký
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        // Phân tích token
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        // Xác thực token và kiểm tra thời hạn
        boolean isValid = signedJWT.verify(verifier) && expiredTime.after(new Date());
        return IntrospectResponse.builder()
                .valid(isValid)
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
                .expirationTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)) // Thời gian hết hạn: 1 ngày
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
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}

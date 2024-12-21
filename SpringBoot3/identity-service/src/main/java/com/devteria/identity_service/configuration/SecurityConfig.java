package com.devteria.identity_service.configuration;

import com.devteria.identity_service.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

/**
 * SecurityConfig là lớp cấu hình bảo mật cho ứng dụng.
 * Được sử dụng để thiết lập các quy tắc bảo mật, xử lý xác thực JWT, và định nghĩa mã hóa mật khẩu.
 */
@Configuration
@EnableWebSecurity // Kích hoạt tính năng bảo mật của Spring Security
@EnableMethodSecurity
public class SecurityConfig {

    // Các endpoint cho phép truy cập công khai với phương thức POST
    private static final String[] PUBLIC_POST_URLS = {
//            "/users",
            "/auth/login",
            "/auth/introspect",
            "/auth/logout",
            "/auth/refresh",
    };

    // Các endpoint yêu cầu quyền ROLE_ADMIN để truy cập với phương thức GET
    private static final String[] ROLE_ADMIN_GET_URLS = {
            "/users"
    };

    // Thuật toán HMAC-SHA512 để mã hóa JWT
    private static final String JWT_ALGORITHM = "HS512";

    // Khóa ký JWT được cấu hình trong file application.properties hoặc application.yml
//    @Value("${jwt.signerKey}")
//    private String signerKey;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    /**
     * Cấu hình SecurityFilterChain để thiết lập các quy tắc bảo mật.
     *
     * @param http HttpSecurity để định nghĩa các quy tắc bảo mật
     * @return SecurityFilterChain đã được cấu hình
     * @throws Exception nếu có lỗi xảy ra trong quá trình cấu hình
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Tắt bảo vệ CSRF cho API REST
                .authorizeHttpRequests(auth -> auth
                        // Các endpoint công khai với phương thức POST
                        .requestMatchers(HttpMethod.POST, PUBLIC_POST_URLS).permitAll()
                        // Các endpoint chỉ cho phép ROLE_ADMIN với phương thức GET
                //        .requestMatchers(HttpMethod.GET, ROLE_ADMIN_GET_URLS).hasRole(Role.ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, ROLE_ADMIN_GET_URLS).hasAuthority("ROLE_ADMIN")
                        // Tất cả các request khác yêu cầu xác thực
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                        .decoder(customJwtDecoder)
//                                .decoder(jwtDecoder()) // Giải mã JWT
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()) // Chuyển đổi JWT sang quyền
                        )
                        .authenticationEntryPoint(new JwtAuthenticationEntrypoint())
                );

        return http.build(); // Trả về cấu hình bảo mật
    }

    /**
     * Chuyển đổi JWT thành quyền sử dụng JwtAuthenticationConverter.
     *
     * @return JwtAuthenticationConverter đã được cấu hình
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        authoritiesConverter.setAuthorityPrefix("ROLE_"); // Thêm tiền tố ROLE_ cho các quyền (SCOPE_ -> ROLE_)
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return authenticationConverter;
    }

    /**
     * Định nghĩa PasswordEncoder sử dụng thuật toán BCrypt để mã hóa mật khẩu.
     *
     * @return PasswordEncoder sử dụng BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); // Mã hóa với độ mạnh 10
    }

    /**
     * Định nghĩa JwtDecoder để giải mã và xác thực token JWT.
     *
     * @return JwtDecoder được cấu hình với khóa ký và thuật toán mã hóa
     */
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        // Kiểm tra tính hợp lệ của signerKey
//        if (signerKey == null || signerKey.isEmpty()) {
//            throw new IllegalStateException("JWT signer key must be provided!");
//        }
//
//        // Tạo SecretKeySpec từ signerKey
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), JWT_ALGORITHM);
//
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec) // Sử dụng SecretKeySpec cho JWT
//                .macAlgorithm(MacAlgorithm.from(JWT_ALGORITHM)) // Thuật toán HMAC-SHA512
////                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }
}
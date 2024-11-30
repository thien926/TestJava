package com.devteria.identity_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

// Đánh dấu class là một class cấu hình bảo mật
@Configuration
@EnableWebSecurity // Kích hoạt bảo mật Web với Spring Security
public class SecurityConfig {

    // Danh sách các endpoint cho phép truy cập công khai với phương thức POST
    private final String[] PUBLIC_POST_URLS = {
            "/users",
            "/auth/login",
            "/auth/introspect"
    };

    protected final String jwtAlgorithm = "HS512";

    // Lấy giá trị SIGNER_KEY từ file cấu hình (application.properties hoặc application.yml)
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    // Cấu hình SecurityFilterChain để thiết lập các quy tắc bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Tắt bảo vệ CSRF (thường được tắt khi sử dụng API REST)
                .csrf(AbstractHttpConfigurer::disable)
                // Định nghĩa các quy tắc ủy quyền
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Cho phép truy cập công khai với phương thức POST tại các URL trong PUBLIC_POST_URLS
                        .requestMatchers(HttpMethod.POST, PUBLIC_POST_URLS).permitAll()
                        // Tất cả các request khác đều yêu cầu xác thực
                        .anyRequest().authenticated()
                );
        // Cấu hình Resource Server để sử dụng OAuth2 và JWT
        http.oauth2ResourceServer(oauth2 -> oauth2
                // Sử dụng decoder được định nghĩa trong phương thức jwtDecoder()
                .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );
        // Trả về SecurityFilterChain đã được cấu hình
        return http.build();
    }

    // Định nghĩa JwtDecoder để giải mã và xác thực token
    @Bean
    public JwtDecoder jwtDecoder() {
        // Kiểm tra SIGNER_KEY
        if (SIGNER_KEY == null || SIGNER_KEY.isEmpty()) {
            throw new IllegalStateException("JWT signer key must be provided!");
        }

        // Tạo khóa bí mật (SecretKeySpec) từ SIGNER_KEY
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), jwtAlgorithm);
        MacAlgorithm macAlgorithm = MacAlgorithm.from(jwtAlgorithm);
        // Sử dụng NimbusJwtDecoder để giải mã JWT với thuật toán HS512
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(macAlgorithm) // Thuật toán ký token HMAC-SHA512
//                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}

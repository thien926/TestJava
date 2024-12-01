package com.devteria.identity_service.configuration;

import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.enums.Role;
import com.devteria.identity_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/**
 * Cấu hình khởi tạo ứng dụng, đảm bảo người dùng mặc định được tạo nếu chưa tồn tại.
 */
@Slf4j
@Configuration
public class ApplicationInitConfig {

    // Inject PasswordEncoder để mã hóa mật khẩu người dùng.
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ApplicationRunner chạy khi ứng dụng khởi động.
     * Tạo người dùng admin mặc định nếu không có trong cơ sở dữ liệu.
     *
     * @param userRepository Repository cho thực thể User.
     * @return ApplicationRunner để kiểm tra và khởi tạo dữ liệu.
     */
    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            // Kiểm tra nếu người dùng 'admin' chưa tồn tại.
            if (!userRepository.findByUsername("admin").isPresent()) {
                // Tạo danh sách vai trò (Set<String>) cho người dùng.
                Set<String> roles = Set.of(Role.ADMIN.name());

                // Xây dựng đối tượng User với thông tin mặc định.
                User user = User.builder()
                        .username("admin")                             // Tên người dùng mặc định.
                        .password(passwordEncoder.encode("admin"))    // Mã hóa mật khẩu.
                        .roles(roles)                                 // Gán vai trò ADMIN.
                        .build();

                // Lưu người dùng vào cơ sở dữ liệu.
                userRepository.save(user);

                // Ghi log cảnh báo thông tin tài khoản admin.
                log.warn("Admin user created: username='admin', password='admin'");
            } else {
                log.info("Admin user already exists. Skipping initialization.");
            }
        };
    }
}

package com.devteria.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Xác định rằng annotation chỉ có thể áp dụng cho các trường
@Target({ FIELD })
// Xác định rằng annotation sẽ tồn tại trong runtime
@Retention(RUNTIME)
// Đánh dấu annotation này là một ràng buộc, liên kết với lớp Dobvalidator để thực hiện logic kiểm tra
@Constraint(validatedBy = { Dobvalidator.class })
public @interface DobConstraint {

    // Xác định thông báo lỗi mặc định nếu kiểm tra thất bại
    String message() default "Invalid date of birth";

    // Cho phép phân nhóm các ràng buộc để áp dụng trong các trường hợp khác nhau
    Class<?>[] groups() default { };

    // Cho phép cung cấp metadata bổ sung (ít được sử dụng)
    Class<? extends Payload>[] payload() default { };

    // Thuộc tính tùy chọn để xác định giá trị tối thiểu (ví dụ: tuổi tối thiểu)
    int min() default 0;
}

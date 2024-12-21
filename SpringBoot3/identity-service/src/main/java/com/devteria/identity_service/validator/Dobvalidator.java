package com.devteria.identity_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

// Lớp Dobvalidator thực hiện logic kiểm tra cho annotation @DobConstraint
// Nó kiểm tra xem giá trị LocalDate có thỏa mãn điều kiện tối thiểu (min) được chỉ định hay không.
public class Dobvalidator implements ConstraintValidator<DobConstraint, LocalDate> {

    // Thuộc tính lưu giá trị tối thiểu (min) từ annotation
    private int min;

    // Phương thức khởi tạo, được gọi khi lớp validator được tạo
    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        // Gọi phương thức khởi tạo của lớp cha (nếu có logic mặc định)
        ConstraintValidator.super.initialize(constraintAnnotation);
        // Lấy giá trị min từ annotation @DobConstraint
        min = constraintAnnotation.min();
    }

    // Phương thức kiểm tra giá trị đầu vào (LocalDate)
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // Nếu giá trị là null, trả về true (có nghĩa là không có lỗi)
        if (Objects.isNull(value)) {
            return true;
        }
        // Tính số năm giữa giá trị đầu vào và ngày hiện tại
        long year = ChronoUnit.YEARS.between(value, LocalDate.now());
        // Trả về true nếu số năm >= min, ngược lại trả về false
        return year >= min;
    }
}

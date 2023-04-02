package com.example.movie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

	private Long id;

	@NotBlank
	@Size(min = 3, max = 100, message = "Title phải từ 3 đến 100 ký tự!")
	private String title;

	private LocalDate releaseDate;

	private String genre;

	@DecimalMin(value = "0.01", message = "Giá phải lớn hơn 0")
	@DecimalMax(value = "999.99", message = "Giá không được vượt quá 999.99")
	private BigDecimal price;

//	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email không hợp lệ")
//	@Email(message = "Email không hợp lệ")
//	private String email;
}

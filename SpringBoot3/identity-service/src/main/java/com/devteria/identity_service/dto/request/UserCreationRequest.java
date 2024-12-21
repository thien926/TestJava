package com.devteria.identity_service.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.devteria.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {
	private String id;

	@NotNull
	@NotBlank
	@Size(min = 3, message = "Username must be as least 3 characters.")
	private String username;
	
	@NotNull
	@NotBlank
	@Size(min = 8, message = "Password must be as least 8 characters.")
	private String password;
	
	private String firstName;
	
	private String lastName;

	@DobConstraint(min = 2)
	private LocalDate dob;

	private List<String> roles;
}

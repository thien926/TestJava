package com.rungroop.web.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubDto {
	
	private Long id;
	
	@NotEmpty(message = "Title shoud not be empty")
	private String title;
	
	@NotEmpty(message = "Photo link shoud not be empty")
	private String photoUrl;
	
	@NotEmpty(message = "Content shoud not be empty")
	private String content;

	private LocalDateTime createdOn;

	private LocalDateTime updatedOn;
}

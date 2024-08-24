package com.rungroop.web.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	
	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title shoud not be empty")
	private String title;
	
	@NotNull(message = "Photo link cannot be null")
	@NotEmpty(message = "Photo link shoud not be empty")
	private String photoUrl;
	
	@NotNull(message = "Content cannot be null")
	@NotEmpty(message = "Content shoud not be empty")
	private String content;

	private LocalDateTime createdOn;

	private LocalDateTime updatedOn;
	
	private Set<EventDto> events;
}

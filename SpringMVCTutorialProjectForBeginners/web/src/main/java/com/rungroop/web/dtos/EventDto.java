package com.rungroop.web.dtos;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
	private Long id;
	
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name shoud not be empty")
	private String name;
	
	@NotNull(message = "StartTime cannot be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startTime;
	
	@NotNull(message = "EndTime cannot be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endTime;
	
	@NotNull(message = "Type cannot be null")
	@NotEmpty(message = "Type shoud not be empty")
	private String type;
	
	@NotNull(message = "PhotoUrl cannot be null")
	@NotEmpty(message = "PhotoUrl shoud not be empty")
	private String photoUrl;
	
	private LocalDateTime createdOn;

	private LocalDateTime updatedOn;
	
	private ClubDto club;
}

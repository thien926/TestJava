package com.rungroop.web.dtos;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
	
	private String name;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	private String type;
	
	private String photoUrl;
	
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@CreationTimestamp
	private LocalDateTime updatedOn;
	
	private ClubDto club;
}

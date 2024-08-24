package com.rungroop.web.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@ManyToOne
	@JoinColumn(name = "club_id", nullable = false)
	private Club club;
}

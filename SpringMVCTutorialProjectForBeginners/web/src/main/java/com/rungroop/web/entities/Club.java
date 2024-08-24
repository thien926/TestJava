package com.rungroop.web.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "clubs")
public class Club {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
	private LocalDateTime updatedOn;
	
	@OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
	private Set<Event> events = new HashSet<>();
}

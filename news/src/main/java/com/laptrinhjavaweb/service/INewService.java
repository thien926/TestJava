package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.dto.NewDTO;

public interface INewService {
	NewDTO save(NewDTO newDTO);
	NewDTO update(NewDTO newDTO);
	NewDTO findById(Long id);
	List<NewDTO> findAll();
	List<NewDTO> findAllByPageNo(int page, int limit);
	void delete(List<Long> ids);
	int totalItem();
	NewDTO findByContent(String content);
	List<NewDTO> findByContentLike(String content);
	long countByContentLike(String content);
	List<NewDTO> findByContentAndTitleLike(String content);
}

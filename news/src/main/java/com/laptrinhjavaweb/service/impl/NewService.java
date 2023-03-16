package com.laptrinhjavaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.NewConverter;
import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.entity.CategoryEntity;
import com.laptrinhjavaweb.entity.NewEntity;
import com.laptrinhjavaweb.repository.CategoryRepository;
import com.laptrinhjavaweb.repository.NewRepository;
import com.laptrinhjavaweb.service.INewService;

@Service
public class NewService implements INewService {
	@Autowired
	private NewRepository newRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NewConverter newConverter;

	@Override
	public NewDTO save(NewDTO newDTO) {
		CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
		NewEntity newEntity = newConverter.toEntity(newDTO);
		newEntity.setCategory(categoryEntity);
		newEntity = newRepository.save(newEntity);
		return newConverter.toDTO(newEntity);
	}

	@Override
	public NewDTO update(NewDTO newDTO) {
		Optional<NewEntity> oldNew = newRepository.findById(newDTO.getId());
		if (oldNew.isEmpty()) {
			return null;
		}
		NewEntity newEntity = newConverter.toEntity(newDTO, oldNew.get());
		CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
		newEntity.setCategory(categoryEntity);
		newEntity = newRepository.save(newEntity);
		return newConverter.toDTO(newEntity);
	}

	@Override
	public void delete(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
//			Iterable<Long> iterable = Arrays.asList(ids);
			newRepository.deleteAllById(ids);
//			for (long item : ids) {
//				newRepository.deleteById(item);
//			}
		}
	}

	@Override
	public NewDTO findById(Long id) {
		Optional<NewEntity> option = newRepository.findById(id);
		NewEntity entity = null;
		if (option.isPresent()) {
			entity = option.get();
		}
		return newConverter.toDTO(entity);
	}

	@Override
	public List<NewDTO> findAll() {
		return newConverter.toListDTO(newRepository.findAll());
	}

	@Override
	public List<NewDTO> findAllByPageNo(int page, int limit) {
		PageRequest pageRequest = PageRequest.of(page-1, limit);
		Page<NewEntity> entities = newRepository.findAll(pageRequest);
		return newConverter.toListDTO(entities.toList());
	}

	@Override
	public int totalItem() {
		return (int) newRepository.count();
	}

	@Override
	public NewDTO findByContent(String content) {
		Optional<NewEntity> option = newRepository.findByContent(content);
		NewEntity entity = null;
		if (option.isPresent()) {
			entity = option.get();
		}
		return newConverter.toDTO(entity);
	}

	@Override
	public List<NewDTO> findByContentLike(String content) {
		Optional<List<NewEntity>> option = newRepository.findByContentContainingIgnoreCase(content);
		List<NewEntity> entity = null;
		if (option.isPresent()) {
			entity = option.get();
		}
		return newConverter.toListDTO(entity);
	}

	@Override
	public List<NewDTO> findByContentAndTitleLike(String content) {
		Optional<List<NewEntity>> option = newRepository.findByContentContainingIgnoreCaseAndTitleContainingIgnoreCase(content, content);
		List<NewEntity> entity = null;
		if (option.isPresent()) {
			entity = option.get();
		}
		return newConverter.toListDTO(entity);
	}

	@Override
	public long countByContentLike(String content) {
		return newRepository.countByContentContainingIgnoreCase(content);
	}
}

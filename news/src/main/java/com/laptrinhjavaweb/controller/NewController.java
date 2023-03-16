package com.laptrinhjavaweb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.response.PagedResponse;
import com.laptrinhjavaweb.response.Response;
import com.laptrinhjavaweb.service.INewService;

@CrossOrigin
@RestController //@Controller + @ResponseBody
public class NewController {
	
	@Value("${app.name}")
	private String appName;
	
	@Value("${app.version}")
	private String appVersion;
	
	@Autowired
	private INewService newService;
	
	@GetMapping("/version")
	public String getDetail() {
		return appName + " - " + appVersion;
	}
	
	@GetMapping("/new")
	public Response<List<NewDTO>> getAll() {
		Response<List<NewDTO>> res = new Response<List<NewDTO>>(true, null, null, newService.findAll());
		return res;
	}
	
	@GetMapping(value = "/new/getByPageNo")
	public PagedResponse<List<NewDTO>> getByPageNo(@RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
		List<NewDTO> listDTO = newService.findAllByPageNo(page, limit);
		int pageCount = (int)Math.ceil(newService.totalItem() / (double)limit);
		PagedResponse<List<NewDTO>> res = new PagedResponse<List<NewDTO>>(listDTO, true, null, null, page, limit, pageCount, listDTO.size());
		return res;
	}
	
	@GetMapping("/new/{id}")
	public Response<NewDTO> getNew(@PathVariable("id") long id) {
		Response<NewDTO> res = new Response<NewDTO>(true, null, null, newService.findById(id));
		return res;
	}
	
	@GetMapping("/new/getByContent/{content}")
	public Response<NewDTO> getByContent(@PathVariable("content") String content) {
		Response<NewDTO> res = new Response<NewDTO>(true, null, null, newService.findByContent(content));
		return res;
	}
	
	@GetMapping("/new/getByContentLike/{content}")
	public Response<List<NewDTO>> getByContentLike(@PathVariable("content") String content) {
		Response<List<NewDTO>> res = new Response<List<NewDTO>>(true, null, null, newService.findByContentLike(content));
		return res;
	}
	
	@GetMapping("/new/countByContentLike/{content}")
	public Response<Long> countByContentLike(@PathVariable("content") String content) {
		Response<Long> res = new Response<Long>(true, null, null, newService.countByContentLike(content));
		return res;
	}
	
	@GetMapping("/new/getByContentLikeAndTitleLike/{content}")
	public Response<List<NewDTO>> getByContentLikeAndTitleLike(@PathVariable("content") String content) {
		Response<List<NewDTO>> res = new Response<List<NewDTO>>(true, null, null, newService.findByContentAndTitleLike(content));
		return res;
	}

	@PostMapping("/new")
	public Response<NewDTO> createNew(@RequestBody @Valid NewDTO model) {
		Response<NewDTO> res = new Response<NewDTO>(true, null, null, newService.save(model));
		return res;
	}

	@PutMapping(value = "/new/{id}")
	public Response<NewDTO> updateNew(@RequestBody @Valid NewDTO model, @PathVariable("id") long id) {
		model.setId(id);
		Response<NewDTO> res = new Response<NewDTO>(true, null, null, newService.update(model));
		return res;
	}

	@DeleteMapping(value = "/new")
	public boolean deleteNew(@RequestParam List<Long> ids) {
		newService.delete(ids);
		return true;
	}
}
package com.soldesk.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.CategoryVO;
import com.soldesk.ex01.service.CategoryService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/category")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/regist")
	public ResponseEntity<Integer> registerPost(@RequestParam("categoryId") int categoryId, @RequestParam("categoryTitle") String categoryTitle) {
		log.info("category controller : registerPost()");
		CategoryVO categoryVO = new CategoryVO();
		categoryVO.setCategoryId(categoryId);
		categoryVO.setCategoryTitle(categoryTitle);
		log.info("category controller : CategorydVO ="+categoryVO);
		int result = categoryService.insertCategory(categoryVO);
		log.info(result+"�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/update")
	public ResponseEntity<Integer> updatePost(@RequestParam("categoryId") int categoryId, @RequestParam("categoryTitle") String categoryTitle) {
		log.info("category controller : updatePost()");
		CategoryVO categoryVO = new CategoryVO();
		categoryVO.setCategoryId(categoryId);
		categoryVO.setCategoryTitle(categoryTitle);
		int result = categoryService.updateCategoryTitle(categoryVO);
		log.info(result+"�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

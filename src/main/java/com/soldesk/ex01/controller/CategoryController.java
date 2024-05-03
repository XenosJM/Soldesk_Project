package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.CategoryVO;
import com.soldesk.ex01.service.CategoryService;

import lombok.extern.log4j.Log4j;

@Controller 
@RequestMapping(value="/category")
@Log4j
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public void list(Model model) {
		log.info("category contoroller : list()");
		List<CategoryVO> categoryList = categoryService.selectCategoryList();
		
		model.addAttribute("categoryList", categoryList);
	}
	
	@GetMapping("/regist")
	public void registerGet() {
		log.info("category controller : registerGet()");
	}
	
	@PostMapping("/regist")
	public String registerPost(CategoryVO vo, RedirectAttributes reAttr) {
		log.info("category controller : registerPost()");
		log.info("category controller : CategorydVO ="+vo);
		int result = categoryService.insertCategory(vo);
		log.info(result+"행 수정");
		return "redirect:/";
	}
	
	@GetMapping("/update")
	public void updateGet(Model model, Integer categoryId) {
		log.info("category controller : updateGet()");
		CategoryVO categoryVO = categoryService.selectCategory(categoryId);
		model.addAttribute("categoryVO", categoryVO);
	}
	
	@PostMapping("/update")
	public String updatePost(CategoryVO vo, RedirectAttributes reAttr) {
		log.info("category controller : updatePost()");
		int result = categoryService.updateCategoryTitle(vo);
		log.info(result+"행 수정");
		return "redirect:/category/list";
	}
}

package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.CategoryVO;
import com.soldesk.ex01.persistence.CategoryMapper;
import com.soldesk.ex01.persistence.RoleMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CategoryServiceImple implements CategoryService {

	@Autowired
	CategoryMapper categoryMapper;
	
	@Autowired
	RoleMapper roleMapper;
	
	@Transactional
	@Override
	public int insertCategory(CategoryVO vo) {
		log.info("service : insertCategory()");
		int result = categoryMapper.insertCategory(vo);
		result = roleMapper.insert();
		return result;
	}

	@Override
	public List<CategoryVO> selectCategoryList() {
		log.info("service : selectCategoryList()");
		List<CategoryVO> list = categoryMapper.selectCategoryList();
		return list;
	}

	@Override
	public List<CategoryVO> selectCategoryTitle(String categoryTitle) {
		log.info("service : selectCategoryTitle()");
		List<CategoryVO> list = categoryMapper.selectCategoryTitle(categoryTitle);
		return list;
	}

	@Transactional
	@Override
	public int updateCategoryTitle(CategoryVO vo) {
		log.info("service : updateCategoryTitle()");
		int result = categoryMapper.updateCategoryTitle(vo);
		return result;
	}

	@Transactional
	@Override
	public int deleteCategory(int categoryId) {
		log.info("service : deleteCategory()");
		int result = categoryMapper.deleteCategory(categoryId);
		return result;
	}

	@Override
	public CategoryVO selectCategory(int categoryId) {
		log.info("service : selectCategory()");
		CategoryVO vo = categoryMapper.selectCategory(categoryId);
		return vo;
	}

	

}

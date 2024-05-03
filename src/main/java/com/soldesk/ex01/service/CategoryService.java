package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.CategoryVO;

public interface CategoryService {
	int insertCategory(CategoryVO vo);
	List<CategoryVO> selectCategoryList();
	List<CategoryVO> selectCategoryTitle(String categoryTitle);
	int updateCategoryTitle(CategoryVO vo);
	int deleteCategory(int categoryId);
	CategoryVO selectCategory(int categoryId);
}

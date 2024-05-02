package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.CategoryVO;

@Mapper
public interface CategoryMapper {
	int insertCategory(CategoryVO vo);
	List<CategoryVO> selectCategoryList();
	List<CategoryVO> selectCategoryTitle(String categoryTitle);
	int updateCategoryTitle(CategoryVO vo);
	int deleteCategory(int categoryId);
}

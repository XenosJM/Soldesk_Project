package com.soldesk.ex01.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.CategoryVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // Junit class 테스트 연결
@ContextConfiguration(classes = {RootConfig.class}) // 설정 파일 연결
@Log4j
public class CategoryMapperTest {

	@Autowired
	CategoryMapper categoryMapper;
	
	@Test
	public void test() {
		//testInsertCategory();
		//testSelectCategory();
		//testUpdateCategory();
		//testDeleteCategory();
		testSelectCategoryByTitle();
		
	}

	private void testInsertCategory() {
		CategoryVO vo = new CategoryVO(1,"페이트그랜드오더");
		int result = categoryMapper.insertCategory(vo);
		System.out.println(result+"행 삽입");		
		
	}
	
	private void testSelectCategory() {
		List<CategoryVO> vo = categoryMapper.selectCategoryList();
		for(int i =0;i<vo.size();i++) {
			System.out.println(vo.get(i));
		}
	}
	
	private void testUpdateCategory() {
		CategoryVO vo = new CategoryVO(5,"마작일번가");
		int result = categoryMapper.updateCategoryTitle(vo);
		System.out.println(result+"행 수정");
	}
	
	private void testDeleteCategory() {
		int result = categoryMapper.deleteCategory(7);
		System.out.println(result+"행 삭제");
	}
	
	private void testSelectCategoryByTitle() {
		List<CategoryVO> vo = categoryMapper.selectCategoryTitle("마작");
		for(int i =0;i<vo.size();i++) {
			System.out.println(vo.get(i));
		}
	}
}

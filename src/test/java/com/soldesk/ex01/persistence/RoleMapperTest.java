package com.soldesk.ex01.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.RoleVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class RoleMapperTest {
	
	@Autowired
	private RoleMapper managerMapper;
	
	@Test
	public void test() {
		testManagerInsert();
//		testGetDetail();
//		testGetAll();
//		testManagerUpdate();
//		testDelete();
	}


	private void testDelete() {
		log.info("testDelete()");
		int result = managerMapper.delete(2);
		log.info(result + "행 삭제");
		
	}

//	private void testGetAll() {
//		log.info("testGetAll()");
//		for(RoleVO managerVO : managerMapper.selectAllManager()) {
//			log.info(managerVO); 
//		}
//		
//	}
	
	private void testManagerInsert() {
		log.info("testManagerInsert()");
		RoleVO managerVO = new RoleVO(0, 1, "dsa");
		int result = managerMapper.insert(managerVO);
		log.info(result + "행 삽입");
		
		
	}
}

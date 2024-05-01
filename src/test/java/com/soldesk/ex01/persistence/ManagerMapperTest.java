package com.soldesk.ex01.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.ManagerVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class ManagerMapperTest {
	
	@Autowired
	private ManagerMapper managerMapper;
	
	@Test
	public void test() {
		testManagerInsert();
		testGetDetail();
		testGetAll();
		testManagerUpdate();
		testDelete();
	}


	private void testDelete() {
		log.info("testDelete()");
		int result = managerMapper.delete(1);
		log.info(result + "행 삭제");
		
	}


	private void testManagerUpdate() {
		log.info("testUpdate()");
		ManagerVO managerVO = new ManagerVO(1, 2, 1);
		int result = managerMapper.update(managerVO);
		log.info(result + "행 수정");
		
	}


	private void testGetAll() {
		log.info("testGetAll()");
		for(ManagerVO managerVO : managerMapper.selectAllManager()) {
			log.info(managerVO); 
		}
		
	}


	private void testGetDetail() {
		log.info("testGetDetail()");
		ManagerVO managerVO = managerMapper.selectByManagerId(1);
		log.info(managerVO);
		
		
	}


	private void testManagerInsert() {
		log.info("testManagerInsert()");
		ManagerVO managerVO = new ManagerVO(0, 1, 1);
		int result = managerMapper.insert(managerVO);
		log.info(result + "행 삽입");
		
	}
}

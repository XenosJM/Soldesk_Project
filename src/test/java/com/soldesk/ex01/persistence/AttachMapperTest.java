package com.soldesk.ex01.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // Junit class 테스트 연결
@ContextConfiguration(classes = {RootConfig.class}) // 설정 파일 연결
@Log4j
public class AttachMapperTest {
	
	@Autowired
	private AttachMapper attachMapper;

	
	@Test
	public void test() {
		testAttachInsert();

	}


	private void testAttachInsert() {
		AttachVO vo = new AttachVO();
		vo.setBoardId(1);
		vo.setAttachId(120);
		vo.setAttachPath("/24/05/14");
		vo.setAttachRealName("test");
		vo.setAttachChgName("sdasdajskdaksmdk");
		vo.setAttachExtension(".txt");
		
		int result = attachMapper.insert(vo);
		log.info(result);
		
	}

	

}

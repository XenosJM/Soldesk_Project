package com.soldesk.ex01.persistence;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // Junit class 테스트 연결
@ContextConfiguration(classes = {RootConfig.class}) // 설정 파일 연결
@Log4j
public class BoardMapperTest {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void test() {
		testBoardInsert();
	}
	
	private void testBoardInsert() {
		BoardVO vo = new BoardVO(0, 1234, 1234, "test", "test", new Date(), 0);
		int result = boardMapper.insertBoard(vo);
		log.info(result + "행 삽입");
	}
}

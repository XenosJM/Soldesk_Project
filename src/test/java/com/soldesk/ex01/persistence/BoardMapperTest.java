package com.soldesk.ex01.persistence;

import java.util.Date;
import java.util.List;

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
		//testBoardInsert();
		//testBoardSelect();
		//testBoardSelectByTitle();
		//testBoardSelectByContent();
		//testUpdateBoard();
		//testDeleteBoard();
		
		testSelectDetail();
	}
	
	private void testBoardInsert() {
//		BoardVO vo = new BoardVO(0, 1234, 1239, "test1", "test", new Date(), 0);
//		int result = boardMapper.insertBoard(vo);
//		log.info(result + "행 삽입");
//		vo = new BoardVO(0, 1234, 1240, "test2", "test", new Date(), 0);
//		result = boardMapper.insertBoard(vo);
//		log.info(result + "행 삽입");
//		vo = new BoardVO(0, 1234, 1241, "test3", "test", new Date(), 0);
//		result = boardMapper.insertBoard(vo);
//		log.info(result + "행 삽입");
//		vo = new BoardVO(0, 1234, 1242, "test4", "test", new Date(), 0);
//		result = boardMapper.insertBoard(vo);
//		log.info(result + "행 삽입");
	}
	
	private void testBoardSelect() {
		List<BoardVO> vo = boardMapper.selectList();
		for(int i =0;i<vo.size();i++) {
			System.out.println(vo.get(i));
		}
	}
	
	private void testBoardSelectByTitle() {
		List<BoardVO> vo = boardMapper.selectByTitle("3");
		for(int i =0;i<vo.size();i++) {
			System.out.println(vo.get(i));
		}
	}
	
	private void testBoardSelectByContent() {
		List<BoardVO> vo = boardMapper.selectByContent("테스트");
		for(int i =0;i<vo.size();i++) {
			System.out.println(vo.get(i));
		}
	}
	
	private void testUpdateBoard() {
//		BoardVO vo = new BoardVO(1,1234,1235,"테스트임","테스트",new Date(),0);
//		int result = boardMapper.updateBoard(vo);
//		System.out.println(result+"행 변경완료");
	}
	
	private void testDeleteBoard() {
		int result = boardMapper.deleteBoard(10);
		System.out.println(result+"행 삭제완료");
	}
	
	private void testSelectDetail() {
		BoardVO vo = boardMapper.selectDetail(2);
		System.out.println(vo);
	}
}

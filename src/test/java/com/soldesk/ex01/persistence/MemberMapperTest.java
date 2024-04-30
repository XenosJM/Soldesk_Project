package com.soldesk.ex01.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // Junit class 테스트 연결
@ContextConfiguration(classes = {RootConfig.class}) // 설정 파일 연결
@Log4j
public class MemberMapperTest {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Test
	public void test() {
		testMemberInsert();
//		testMemberList();
//		testMemberByMemberId();
//		testMemberUpdate();
//		testMemberDelete();
	}

	private void testMemberInsert() {
		MemberVO vo = new MemberVO(0, "wjdals", "1q2w3e4r", 1, "wjdalsqaaz123@gmail.com", null, null);
		int result = memberMapper.insert(vo);
		log.info(result + "행 삽입");
	}
	
}

package com.soldesk.ex01.persistence;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // Junit class �׽�Ʈ ����
@ContextConfiguration(classes = {RootConfig.class}) // ���� ���� ����
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
//		testMemberPropertyUpdate();
//		testMemberManagerUpdate();
//		testMemberDelete();
	}
	
	private void testMemberDelete() {
		log.info("testMemberDelete()");
		int result = memberMapper.delete("wjdals99");
		log.info(result + "�� ����");
	}

	private void testMemberManagerUpdate() {
		MemberVO vo = new MemberVO("wjdals99", null, 2, null, null, null, null, null);
		int result = memberMapper.updateManager(vo);
		log.info(result + "�� ����");
		
	}

	private void testMemberPropertyUpdate() {
		MemberVO vo = new MemberVO();
		vo.setMemberId("wjdals99");
		vo.setMemberProperty(new Integer[] {34, 25, 22});
		int result = memberMapper.updateProperty(vo);
		log.info(result + "�� ����");
	}

	private void testMemberUpdate() {
		log.info("testMemberUpdate()");
		MemberVO vo = new MemberVO(); 
		vo.setMemberId("wjdals99");
		vo.setMemberPassword("456456");
		vo.setMemberEmail("test@test.com");
		int result = memberMapper.update(vo);
		log.info(result + "�� ����");
	}

	private void testMemberByMemberId() {
		log.info("testMemberByMemberId()");
		MemberVO vo = memberMapper.selectByMemberId("wjdals99");
		log.info(vo);
		
	}

	private void testMemberList() {
		log.info("testMemberList()");
		for(MemberVO memberVO : memberMapper.selectIdList()) {
			log.info(memberVO);
		}
	}

	private void testMemberInsert() {
		MemberVO vo = new MemberVO();
		vo.setMemberId("test2");
		vo.setMemberPassword("test");
		vo.setMemberEmail("test");
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String endcodePw = encoder.encode(vo.getMemberPassword());
		vo.setMemberPassword(endcodePw);
//		vo.setMemberPropertyAsString(Arrays.toString(vo.getMemberProperty()));
		int result = memberMapper.insert(vo);
		log.info(result + "�� ����");
	}
	
}

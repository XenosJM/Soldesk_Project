package com.soldesk.ex01.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // Junit class �׽�Ʈ ����
@ContextConfiguration(classes = {RootConfig.class}) // ���� ���� ����
@Log4j
public class ReplyMapperTest {
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Test
	public void test() {
		//testReplyInsert();
		//testReplySelectList();
		//testReplySelectByMember();
		testReplySelectByContent();
		//testReplyUpdate();
		//testReplyDelete();
	}





	public void testReplyInsert() {
//		ReplyVO vo = new ReplyVO(0,1,1234,1234,"����׽�Ʈ2",new Date());
//		int result = replyMapper.insertReply(vo);
//		System.out.println(result+"�� ���ԿϷ�");
//		vo = new ReplyVO(0,1,1234,1234,"����׽�Ʈ3",new Date());
//		result = replyMapper.insertReply(vo);
//		System.out.println(result+"�� ���ԿϷ�");
//		vo = new ReplyVO(0,1,1234,1234,"����׽�Ʈ4",new Date());
//		result = replyMapper.insertReply(vo);
//		System.out.println(result+"�� ���ԿϷ�");
//		vo = new ReplyVO(0,1,1234,1234,"test1",new Date());
//		result = replyMapper.insertReply(vo);
//		System.out.println(result+"�� ���ԿϷ�");
//		vo = new ReplyVO(0,1,1234,1234,"test2",new Date());
//		result = replyMapper.insertReply(vo);
//		System.out.println(result+"�� ���ԿϷ�");
//		vo = new ReplyVO(0,1,1234,1234,"test3",new Date());
//		result = replyMapper.insertReply(vo);
//		System.out.println(result+"�� ���ԿϷ�");
		
	}
	
	private void testReplySelectList() {
		List<ReplyVO> list = replyMapper.selectReplyBoard(1);
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));
		}
	}
	
	private void testReplySelectByMember() {
		List<ReplyVO> list = replyMapper.selectReplyMemberList("975");
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));
		}
	}
	
	private void testReplySelectByContent() {
		List<ReplyVO> list = replyMapper.selectReplyContentList("test");
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));
		}
		
	}
	
	private void testReplyUpdate() {
//		ReplyVO vo = new ReplyVO(1,1,1234,1234,"�����ȴ��",new Date());
//		int result = replyMapper.updateReply(vo);
//		System.out.println(result + "�� ����");
		
	}
	
	private void testReplyDelete() {
		int result = replyMapper.deleteReply(5);
		System.out.println(result + "�� ����");
	}
}

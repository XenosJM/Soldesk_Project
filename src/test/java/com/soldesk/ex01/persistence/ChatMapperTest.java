package com.soldesk.ex01.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.soldesk.ex01.config.RootConfig;
import com.soldesk.ex01.domain.ChatVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class ChatMapperTest {
	
	@Autowired
	private ChatMapper chat;
	
	@Test
	public void test() {
//		testChatInsert();
//		testChatSelect("test2", "test");
//		testChatGroupSelect();
		testUpdateChat();
		
	}

	private void testUpdateChat() {
		ChatVO chatVO = new ChatVO();
		int result = chat.deleteChat(chatVO);
		log.info(result);
		
	}

	private void testChatGroupSelect() {
		for(ChatVO chatVO : chat.selectGroupChat(0)) {
			log.info(chatVO);
		}
		
	}

	private void testChatSelect(String memberId, String chatMemberId) {
		ChatVO chatVO = new ChatVO();
		for(ChatVO VO : chat.selectPrivateChat(chatVO)) {
			
			log.info(VO);
		}
	}

	private void testChatInsert() {
		ChatVO chatVO = new ChatVO();
		chatVO.setChatContent("테스트");
		chatVO.setMemberId("test2");
		chatVO.setChatMemberId("test");
		int result = chat.insertChat(chatVO);
		
	}

}

package com.soldesk.ex01.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.ChatGroupVO;
import com.soldesk.ex01.domain.ChatVO;
import com.soldesk.ex01.persistence.ChatGroupMapper;
import com.soldesk.ex01.persistence.ChatMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ChatServiceImple implements ChatService {
	
	@Autowired
	private ChatMapper chat;
	
	@Autowired
	private ChatGroupMapper group;
	
	@Override
	public int recordChat(ChatVO chatVO) {
		return chat.insertChat(chatVO);
	}

	@Override
	public List<ChatVO> getPrivateChatRecord(ChatVO chatVO) {
		return chat.selectPrivateChat(chatVO);
	}

	@Override
	public List<ChatVO> getGroupChatRecord(ChatVO chatVO) {
		return chat.selectGroupChat(chatVO.getChatGroupId());
	}
	
	@Override
	public int exitGroup(int chatGroupId, String memberId) {
		ChatGroupVO groupVO = group.selectGroupById(chatGroupId);
		ChatGroupVO changeVO = new ChatGroupVO();
		List<String> list = new ArrayList<String>();
		for(String item : groupVO.getChatMemberAsString().split(",")) {
			list.add(item);
		}
		list.remove(memberId);
		
		changeVO.setChatMember((String[]) list.toArray());
		changeVO.setChatGroupId(chatGroupId);
		changeVO.setGroupTitle(groupVO.getGroupTitle());
		
		return group.updateChatMember(groupVO);
	}

	@Override
	public int deleteChatRecord(ChatVO chatVO) {
		return chat.deleteChat(chatVO);
	}

	@Override
	public int removeChatRecord(int chatGroupId) {
		return chat.removeChat(chatGroupId);
	}

}

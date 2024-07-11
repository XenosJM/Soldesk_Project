package com.soldesk.ex01.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.ChatGroupVO;
import com.soldesk.ex01.persistence.ChatGroupMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ChatGroupServiceImple implements ChatGroupService {
	
	@Autowired
	private ChatGroupMapper group;

	@Override
	public int createGroup(ChatGroupVO groupVO) {
		return group.insertChatGroup(groupVO);
	}

	@Override
	public List<ChatGroupVO> getGroupList(String memberId) {
		return group.selectChatGroup(memberId);
	}
	
	@Override
	public int updateGroupTitle(ChatGroupVO groupVO) {
		return group.updateChatGroupTitle(groupVO);
	}

	@Override
	public int deleteGroup(int chatGroupId) {
		return group.deleteChatGroup(chatGroupId);
	}

	@Override
	public int inviteMember(int chatGroupId, String memberId) {
		ChatGroupVO groupVO = group.selectGroupById(chatGroupId);
		List<String> list = new ArrayList<String>();
		for(String item : groupVO.getChatMember().split(",")) {
			list.add(item);
		}
		list.add(memberId);
		groupVO.setChatMember((String[]) list.toArray());
		
		return group.updateChatMember(groupVO);
	}

}

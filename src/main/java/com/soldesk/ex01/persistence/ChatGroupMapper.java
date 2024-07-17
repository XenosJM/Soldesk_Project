package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.ChatGroupVO;

@Mapper
public interface ChatGroupMapper {
	
	int insertChatGroup(ChatGroupVO groupVO);
	List<ChatGroupVO> selectChatGroup(String memberId);
	ChatGroupVO selectGroupById(int chatGroupId);
	int updateChatGroupTitle(ChatGroupVO groupVO);
	int updateChatMember(ChatGroupVO groupVO);
	int deleteChatGroup(int chatGroupId);
}

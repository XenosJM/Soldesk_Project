package com.soldesk.ex01.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.ChatGroupVO;

public interface ChatGroupService {
	
	// 채팅방 개설
	int createGroup(ChatGroupVO groupVO);
	// 채팅방 목록(1대1 채팅방 포함)
	List<ChatGroupVO> getGroupList(String memberId);
	// 채팅방 초대
	int inviteMember(@Param("chatGroupId") int chatGroupId,@Param("memberId") String memberId);
	// 채팅방 이름 변경
	int updateGroupTitle(ChatGroupVO groupVO);
	// 채팅방 삭제(아무도 없는 경우)
	int deleteGroup(int chatGroupId);
}

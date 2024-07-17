package com.soldesk.ex01.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.ChatVO;

public interface ChatService {
	
	// 채팅 내역 기록
	int recordChat(ChatVO chatVO);
	// 개인 채팅 내역 가져오기
	List<ChatVO> getPrivateChatRecord(ChatVO chatVO);
	// 그룹 채팅 내역 가져오기
	List<ChatVO> getGroupChatRecord(ChatVO chatVO);
	// 채팅방 나가기
	int exitGroup(@Param("chatGroupId") int chatGroupId,@Param("membeId") String memberId);
	// 채팅 기록 삭제
	int deleteChatRecord(ChatVO chatVO);
	// 3일이 지난 대화 내역 삭제
	int removeChatRecord(int chatGroupId);
}

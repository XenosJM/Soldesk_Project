package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.ChatVO;

@Mapper
public interface ChatMapper {
	
	int insertChat(ChatVO chatVO);
	List<ChatVO> selectPrivateChat(@Param(value = "memberId") String memberId,@Param(value = "chatMemberId") String chatMemberId);
	List<ChatVO> selectGroupChat(int chatGroupId);
	int updateChat(@Param(value = "chatId")int chatId, @Param(value = "chatGroupId") int chatGroupId);
	int deleteChat(int chatGroupId);
}

package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.ChatGroupVO;
import com.soldesk.ex01.domain.ChatVO;

@Mapper
public interface ChatMapper {
	
	int insertChat(ChatVO chatVO);
	List<ChatVO> selectPrivateChat(ChatVO chatVO);
	List<ChatVO> selectGroupChat(int chatGroupId);
	int deleteChat(ChatVO chatVO);
	int removeChat(int chatGroupId);
}

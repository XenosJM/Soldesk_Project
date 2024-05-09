package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;

@Mapper
public interface FriendMapper {
	
	int insertRequest(RequestVO requestVO);
	List<RequestVO> sendListRequest(int memberId);
	int requestStateChange(int requestId);
	int cancelRequest(int requestId);
	
	int insertReceive(ReceiveVO receiveVO);
	List<ReceiveVO> receiveListRequest(int memberId);
	int receiveStateChange(int receiveId);
	int rejectRequest(int receiveId);
	
	int insertFriend(FriendVO friendVO);
	List<FriendVO> friendList(int memberId);
	int friendStatechange(int friendshipId);
	int deleteFriend(int friendshipId);
	
}

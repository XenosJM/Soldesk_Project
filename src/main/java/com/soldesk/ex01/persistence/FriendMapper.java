package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;

@Mapper
public interface FriendMapper {
	
	int insertRequest(RequestVO requestVO);
	List<RequestVO> sendListRequest(String memberId);
	String selectRequestById(String memberId);
	List<RequestVO> allSendList();
	int requestStateChange(int requestId, String requestState);
	int cancelRequest(int requestId);
	
	int insertReceive(ReceiveVO receiveVO);
	List<ReceiveVO> receiveListRequest(String memberId);
	int receiveStateChange(int receiveId, String receiveState);
	List<ReceiveVO> allReceiveList();
	int rejectRequest(int receiveId);
	
	int insertFriend(FriendVO friendVO);
	List<FriendVO> friendList(String memberId);
	int friendStateChange(String memberId, String friendState);
	List<FriendVO> allFriend();
	int deleteFriend(int friendshipId);
	
}

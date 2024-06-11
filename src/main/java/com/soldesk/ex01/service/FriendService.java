package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;

public interface FriendService {
	
	int insertRequest(RequestVO requestVO);
	List<RequestVO> sendList(String memberId);
	int requestStateChange(int requestId, String requestState);
	int cancelRequest(int requestId);
	
	int insertReceive(ReceiveVO receiveVO);
	List<ReceiveVO> receiveList(String memberId);
	int receiveStateChange(int receiveId, String receiveState);
	int rejectRequest(int receiveId);
	
	int insertFriend(FriendVO friendVO);
	List<FriendVO> friendList(String memberId);
	int friendStateChange(String memberId, String friendState);
	int deleteFriend(int friendshipId);
}

package com.soldesk.ex01.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;

public interface FriendService {
	
	int insertFriend(FriendVO friendVO);
	List<FriendVO> friendList(String memberId);
	int friendStateChange(@Param("memberId") String memberId,@Param("friendState") String friendState);
	int deleteFriend(FriendVO friendVO);
}

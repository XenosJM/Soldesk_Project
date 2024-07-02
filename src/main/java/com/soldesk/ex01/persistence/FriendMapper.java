package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;

@Mapper
public interface FriendMapper {
	
	int insertFriend(FriendVO friendVO);
	List<FriendVO> friendList(String memberId);
	int friendStateChange(@Param("memberId") String memberId,@Param("friendState") String friendState);
	List<FriendVO> allFriend();
	int deleteFriend(int friendshipId);
	
}

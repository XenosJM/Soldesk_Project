package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;
import com.soldesk.ex01.persistence.FriendMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FriendServiceImple implements FriendService {
	
	@Autowired
	private FriendMapper friendMapper;
	
	
	@PreAuthorize("isAuthenticated() and (#friendVO.memberId == principal.username) or (#friendVO.friendMemberId == principal.username)")
	@Override
	public int insertFriend(FriendVO friendVO) {
		log.info("insertFriend()");
		int result = friendMapper.insertFriend(friendVO);
		return result;
	}
	
	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public List<FriendVO> friendList(String memberId) {
		log.info("friendList()");
		List<FriendVO> list = friendMapper.friendList(memberId);
		log.info(list);
		return list;
	}
	
	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public int friendStateChange(String memberId, String friendState) {
		log.info("friendStateChange()");
		int result = friendMapper.friendStateChange(memberId, friendState);
		return result;
	}
	
	@PreAuthorize("isAuthenticated()")
	@Override
	public int deleteFriend(FriendVO friendVO) {
		log.info("deleteFriend()");
		int result = friendMapper.deleteFriend(friendVO);
		return result;
	}

}

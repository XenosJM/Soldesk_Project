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
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#requestVO.memberId == principal.username)")
	@Override
	public int insertRequest(RequestVO requestVO) {
		log.info("insertRequest()");		
		int result = 0;
		if(friendMapper.selectRequestById(requestVO.getMemberId()) != null) {
			result = 2; // request 요청목록에 현재 요청한 정보가 있다면
			return result;
		} else {
			result = friendMapper.insertRequest(requestVO);
		}
		return result;
	}

	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public List<RequestVO> sendList(String memberId) {
		log.info("sendList");
		return friendMapper.sendListRequest(memberId);
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int requestStateChange(int requestId, String requestState) {
		log.info("requestStateChange()");
		log.info(requestState);
		int result = friendMapper.requestStateChange(requestId, requestState);
		return result;
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int cancelRequest(int requestId) {
		log.info("cancleRequest()");
		int result = friendMapper.cancelRequest(requestId);
		return result;
	}
	@PreAuthorize("isAuthenticated() and (#receiveVO.memberId == principal.username)")
	@Override
	public int insertReceive(ReceiveVO receiveVO) {
		log.info("insertReceive()");
		int result = friendMapper.insertReceive(receiveVO);
		return result;
	}
	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public List<ReceiveVO> receiveList(String memberId) {
		log.info("receiveList()");
		return friendMapper.receiveListRequest(memberId);
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int receiveStateChange(int receiveId, String receiveState) {
		log.info("receiveStateChange()");
		int result= friendMapper.receiveStateChange(receiveId, receiveState);
		return result;
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int rejectRequest(int receiveId) {
		log.info("rejectRequest()");
		int result = friendMapper.rejectRequest(receiveId);
		return result;
	}
	@PreAuthorize("isAuthenticated() and (#friendVO.memberId == principal.username)")
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
	public int deleteFriend(int friendshipId) {
		log.info("deleteFriend()");
		int result = friendMapper.deleteFriend(friendshipId);
		return result;
	}

}

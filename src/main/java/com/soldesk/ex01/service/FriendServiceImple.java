package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Override
	public int insertRequest(RequestVO requestVO) {
		log.info("insertRequest()");
		ReceiveVO receiveVO = new ReceiveVO();
		receiveVO.setMemberId(requestVO.getReceiverId());
		receiveVO.setRequesterId(requestVO.getMemberId());
		
		int result = 0;
		if(friendMapper.selectRequestById(requestVO.getMemberId()) != null) {
			result = 2;
		} else {
			int req = friendMapper.insertRequest(requestVO);
			int rec = friendMapper.insertReceive(receiveVO);
			
			if(req == rec) {
				result = 1;
			} else {
				result = 0;
			}
		}
		return result;
	}

	@Override
	public List<RequestVO> sendList(String memberId) {
		log.info("sendList");
		return friendMapper.sendListRequest(memberId);
	}

	@Override
	public int requestStateChange(int requestId, String requestState) {
		log.info("requestStateChange()");
		int result = friendMapper.requestStateChange(requestId, requestState);
		return result;
	}

	@Override
	public int cancelRequest(int requestId) {
		log.info("cancleRequest()");
		int result = friendMapper.cancelRequest(requestId);
		return result;
	}

	@Override
	public int insertReceive(ReceiveVO receiveVO) {
		log.info("insertReceive()");
		int result = friendMapper.insertReceive(receiveVO);
		return result;
	}

	@Override
	public List<ReceiveVO> receiveList(String memberId) {
		log.info("receiveList()");
		return friendMapper.receiveListRequest(memberId);
	}

	@Override
	public int receiveStateChange(int receiveId, String receiveState) {
		log.info("receiveStateChange()");
		int result= friendMapper.receiveStateChange(receiveId, receiveState);
		return result;
	}

	@Override
	public int rejectRequest(int receiveId) {
		log.info("rejectRequest()");
		int result = friendMapper.rejectRequest(receiveId);
		return result;
	}

	@Override
	public int insertFriend(FriendVO friendVO) {
		log.info("insertFriend()");
		int result = friendMapper.insertFriend(friendVO);
		return result;
	}

	@Override
	public List<FriendVO> friendList(String memberId) {
		log.info("friendList()");
		List<FriendVO> list = friendMapper.friendList(memberId);
		log.info(list);
		return list;
	}

	@Override
	public int friendStateChange(String memberId, String friendState) {
		log.info("friendStateChange()");
		int result = friendMapper.friendStateChange(memberId, friendState);
		return result;
	}

	@Override
	public int deleteFriend(int friendshipId) {
		log.info("deleteFriend()");
		int result = friendMapper.deleteFriend(friendshipId);
		return result;
	}

}

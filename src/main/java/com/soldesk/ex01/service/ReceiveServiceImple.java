package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.persistence.ReceiveMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReceiveServiceImple implements ReceiveService {
	
	@Autowired
	private ReceiveMapper receive;

	@PreAuthorize("isAuthenticated() and (#receiveVO.memberId == principal.username)")
	@Override
	public int insertReceive(ReceiveVO receiveVO) {
		log.info("insertReceive()");
		int result = receive.insertReceive(receiveVO);
		return result;
	}
	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public List<ReceiveVO> receiveList(String memberId) {
		log.info("receiveList()");
		return receive.receiveListRequest(memberId);
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int receiveStateChange(int receiveId, String receiveState) {
		log.info("receiveStateChange()");
		int result= receive.receiveStateChange(receiveId, receiveState);
		return result;
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int rejectRequest(int receiveId) {
		log.info("rejectRequest()");
		int result = receive.rejectRequest(receiveId);
		return result;
	}

}

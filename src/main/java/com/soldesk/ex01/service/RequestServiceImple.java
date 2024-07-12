package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.RequestVO;
import com.soldesk.ex01.persistence.RequestMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RequestServiceImple implements RequestService {
	
	@Autowired
	private RequestMapper request;
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#requestVO.memberId == principal.username)")
	@Override
	public int insertRequest(RequestVO requestVO) {
		log.info("insertRequest()");		
		int result = 0;
		if(request.selectRequestByMemberId(requestVO.getMemberId()) != null) {
			result = 2; // request 요청목록에 현재 요청한 정보가 있다면
			return result;
		} else {
			result = request.insertRequest(requestVO);
		}
		return result;
	}

	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public List<RequestVO> sendList(String memberId) {
		log.info("sendList");
		return request.sendListRequest(memberId);
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int requestStateChange(int requestId, String requestState) {
		log.info("requestStateChange()");
		log.info(requestState);
		int result = request.requestStateChange(requestId, requestState);
		return result;
	}
	@PreAuthorize("isAuthenticated()")
	@Override
	public int cancelRequest(int requestId) {
		log.info("cancleRequest()");
		int result = request.cancelRequest(requestId);
		return result;
	}

	@Override
	public RequestVO getRequestByReceiverId(String receiverId) {
		RequestVO requestVO = request.selectByReceiverId(receiverId);
		return requestVO;
	}

	@Override
	public RequestVO getByRequestId(int requestId) {
		RequestVO requestVO = request.selectByRequestId(requestId);
		return requestVO;
	}

}

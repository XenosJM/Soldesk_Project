package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.RequestVO;

public interface RequestService {

	int insertRequest(RequestVO requestVO);
	List<RequestVO> sendList(String memberId);
	int requestStateChange(int requestId, String requestState);
	int cancelRequest(int requestId);

}

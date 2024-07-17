package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.ReceiveVO;

public interface ReceiveService {

	int insertReceive(ReceiveVO receiveVO);
	List<ReceiveVO> receiveList(String memberId);
	ReceiveVO getByRequesterId(String requesterid);
	ReceiveVO getByReceiveId(int receiveId);
	int receiveStateChange(int receiveId, String receiveState);
	int rejectRequest(int receiveId);

}

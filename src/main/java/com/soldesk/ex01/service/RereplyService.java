package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.RereplyVO;

public interface RereplyService {
	
	int insertRereply(RereplyVO vo);
	List<RereplyVO>selectRereply(int replyId);
	int updateRereply(RereplyVO vo);
	int deleteRereply(int rereplyId);
	int deleteRereplyToReply(int replyId);
	RereplyVO findRereply(int rereplyId);
	int countRereply(int replyId);
}

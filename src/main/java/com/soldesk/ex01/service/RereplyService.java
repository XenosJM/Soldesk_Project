package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.RereplyVO;

public interface RereplyService {
	int insertRereply(RereplyVO vo);
	List<RereplyVO>selectRereply(int replyId);
	int updateRereply(int rereplyID, String rereplyContent);
	int deleteRereply(int rereplyId);
	int deleteRereplyToReply(int replyId);
}

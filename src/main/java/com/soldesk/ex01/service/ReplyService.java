package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.ReplyVO;

public interface ReplyService {
	int insertReply(ReplyVO vo);
	List<ReplyVO> selectReplyMemberList(String memberId);
	List<ReplyVO> selectReplyContentList(String Content);
	List<ReplyVO> selectReplyBoard(int boardId);
	int updateReply(ReplyVO vo);
	int deleteReply(int replyId);
}

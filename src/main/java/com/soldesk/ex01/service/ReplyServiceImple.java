package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.persistence.ReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImple implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	@Override
	public int insertReply(ReplyVO vo) {
		log.info("service : insertReply()");
		int result = replyMapper.insertReply(vo);
		return result;
	}

	@Override
	public List<ReplyVO> selectReplyMemberList(String memberId) {
		log.info("service : selectReplyMemberList()");
		List<ReplyVO> list = replyMapper.selectReplyMemberList(memberId);
		return list;
	}

	@Override
	public List<ReplyVO> selectReplyContentList(String Content) {
		log.info("service : selectReplyContentList()");
		List<ReplyVO> list = replyMapper.selectReplyContentList(Content);
		return list;
	}

	@Override
	public List<ReplyVO> selectReplyBoard(int boardId) {
		log.info("service : selectReplyBoard()");
		List<ReplyVO> list = replyMapper.selectReplyBoard(boardId);
		return list;
	}

	@Override
	public int updateReply(ReplyVO vo) {
		log.info("service : updateReply()");
		int result = replyMapper.updateReply(vo);
		return result;
	}

	@Override
	public int deleteReply(int replyId) {
		log.info("service : deleteReply()");
		int result = replyMapper.deleteReply(replyId);
		return result;
	}

}

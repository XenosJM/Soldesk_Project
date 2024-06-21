package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.persistence.Board2Mapper;
import com.soldesk.ex01.persistence.ReplyMapper;
import com.soldesk.ex01.persistence.RereplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImple implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	RereplyMapper rereplyMapper;
	
	@Autowired
	Board2Mapper board2Mapper;
	
	@Override
	public int insertReply(ReplyVO vo) {
		log.info("service : insertReply()");
		int result = replyMapper.insertReply(vo);
		result = board2Mapper.increaseReplyCount(vo.getBoardId());
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
	public int updateReply(int replyId, String replyContent) {
		log.info("service : updateReply()");
		ReplyVO replyVO = new ReplyVO();
		replyVO.setReplyId(replyId);
		replyVO.setReplyContent(replyContent);
		int result = replyMapper.updateReply(replyVO);
		return result;
	}

	@Override
	public int deleteReply(int replyId) {
		log.info("service : deleteReply()");
		ReplyVO vo = replyMapper.findReply(replyId);
		log.info(vo);
		int result = replyMapper.deleteReply(replyId);
		
		result = board2Mapper.decreaseReplyCount(vo.getBoardId());
		
		log.info("replycount-1 ��� : "+result);
		return result;
	}
	
	@Override
	public int deleteReplyByBoard(int boardId) {
		log.info("service : deleteReplyByBoard");
		int	result = replyMapper.deleteReplyByBoard(boardId);
		log.info("reply ���� ��� : " + result);

		return result;
	}

}

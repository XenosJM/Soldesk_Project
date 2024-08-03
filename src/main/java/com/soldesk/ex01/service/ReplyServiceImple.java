package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.persistence.BoardMapper;
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
	BoardMapper boardMapper;
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int insertReply(ReplyVO vo) {
		log.info("service : insertReply()");
		int result = replyMapper.insertReply(vo);
		result = boardMapper.increaseReplyCount(vo.getBoardId());
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

	@Transactional
	@PreAuthorize("isAuthenticated()")
	@Override
	public int updateReply(int replyId, String replyContent) {
		log.info("service : updateReply()");
		ReplyVO replyVO = new ReplyVO();
		replyVO.setReplyId(replyId);
		replyVO.setReplyContent(replyContent);
		int result = replyMapper.updateReply(replyVO);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int deleteReply(int replyId) {
		log.info("service : deleteReply()");
		ReplyVO vo = replyMapper.findReply(replyId);
		log.info(vo);
		int boardId = replyMapper.findReply(replyId).getBoardId();
		int result = replyMapper.deleteReply(replyId);
		result = boardMapper.decreaseReplyCount(vo.getBoardId());
		result= boardMapper.decreaseReplyCountByRereply(rereplyMapper.countRereply(replyId), boardId);
		result = rereplyMapper.deleteRereplyToReply(replyId);
		
		log.info("replycount-1 ��� : "+result);
		return result;
	}
	
	@Transactional
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int deleteReplyByBoard(int boardId) {
		log.info("service : deleteReplyByBoard");
		int	result = replyMapper.deleteReplyByBoard(boardId);
		log.info("reply ���� ��� : " + result);

		return result;
	}

}

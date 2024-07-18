package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.RereplyVO;
import com.soldesk.ex01.persistence.BoardMapper;
import com.soldesk.ex01.persistence.ReplyMapper;
import com.soldesk.ex01.persistence.RereplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RereplyServiceImple implements RereplyService {
	
	@Autowired
	RereplyMapper rereplyMapper;
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	BoardMapper boardMapper;
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int insertRereply(RereplyVO vo) {
		log.info("rereply service : insertRereply()");
		int result = rereplyMapper.insertRereply(vo);
		boardMapper.increaseReplyCount(replyMapper.findReply(vo.getReplyId()).getBoardId());
		return result;
	}

	@Override
	public List<RereplyVO> selectRereply(int replyId) {
		log.info("rereply service : selectRereply()");
		List<RereplyVO> list = rereplyMapper.selectRereply(replyId);
		return list;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int updateRereply(RereplyVO vo) {
		log.info("rereply service : updateRereply()");
		log.info(vo);
		int result = rereplyMapper.updateRereply(vo);
		log.info("service result : "+result);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int deleteRereply(int rereplyId) {
		log.info("rereply service : deleteRereply()");
		boardMapper.decreaseReplyCount(replyMapper.findReply(rereplyMapper.findRereply(rereplyId).getReplyId()).getBoardId());
		int result = rereplyMapper.deleteRereply(rereplyId);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int deleteRereplyToReply(int replyId) {
		log.info("rereply service : deleteRereplyToReply()");
		int updateResult = boardMapper.decreaseReplyCountByRereply(rereplyMapper.countRereply(replyId), replyMapper.findReply(replyId).getBoardId());
		int result = rereplyMapper.deleteRereplyToReply(replyId);
		return result;
	}

	@Override
	public RereplyVO findRereply(int rereplyId) {
		return rereplyMapper.findRereply(rereplyId);
	}
	
	@Override
	public int countRereply(int replyId) {
		return rereplyMapper.countRereply(replyId);
	}

}

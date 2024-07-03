package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.RereplyVO;
import com.soldesk.ex01.persistence.RereplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RereplyServiceImple implements RereplyService {
	
	@Autowired
	RereplyMapper rereplymapper;
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int insertRereply(RereplyVO vo) {
		log.info("rereply service : insertRereply()");
		int result = rereplymapper.insertRereply(vo);
		return result;
	}

	@Override
	public List<RereplyVO> selectRereply(int replyId) {
		log.info("rereply service : selectRereply()");
		List<RereplyVO> list = rereplymapper.selectRereply(replyId);
		return list;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int updateRereply(int rereplyId, String rereplyContent) {
		log.info("rereply service : updateRereply()");
		RereplyVO vo = new RereplyVO();
		log.info("1");
		vo.setRereplyId(rereplyId);
		log.info("1");
		vo.setRereplyContent(rereplyContent);
		log.info("1");
		System.out.println(vo);
		int result = rereplymapper.updateRereply(vo);
		log.info("service result : "+result);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int deleteRereply(int rereplyId) {
		log.info("rereply service : deleteRereply()");
		int result = rereplymapper.deleteRereply(rereplyId);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int deleteRereplyToReply(int replyId) {
		log.info("rereply service : deleteRereplyToReply()");
		int result = rereplymapper.deleteRereplyToReply(replyId);
		return result;
	}

}

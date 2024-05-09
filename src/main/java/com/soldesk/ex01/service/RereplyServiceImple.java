package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.RereplyVO;
import com.soldesk.ex01.persistence.RereplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RereplyServiceImple implements RereplyService {
	
	@Autowired
	RereplyMapper rereplymapper;
	
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

	@Override
	public int deleteRereply(int rereplyId) {
		log.info("rereply service : deleteRereply()");
		int result = rereplymapper.deleteRereply(rereplyId);
		return result;
	}

	@Override
	public int deleteRereplyToReply(int replyId) {
		log.info("rereply service : deleteRereplyToReply()");
		int result = rereplymapper.deleteRereplyToReply(replyId);
		return result;
	}

}

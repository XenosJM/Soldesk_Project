package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.persistence.AttachMapper;
import com.soldesk.ex01.persistence.Board2Mapper;
import com.soldesk.ex01.persistence.ReplyMapper;
import com.soldesk.ex01.persistence.RereplyMapper;
import com.soldesk.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class Board2ServiceImple implements Board2Service {
	
	@Autowired
	Board2Mapper board2Mapper;
	
	@Autowired
	AttachMapper attachMapper;
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	RereplyMapper rereplyMapper;
	
	
	@Override
	public int insertBoard(Board2VO vo) {
		log.info("service : insertBoard()");
		int result = board2Mapper.insertBoard(vo);
		log.info("board2Mapper.insert 결과 : "+result);
		//result = attachMapper.insert(vo.getAttachVO());
		for(int i=0;i<vo.getAttachVO().length;i++) {
			result = attachMapper.insert(vo.getAttachVO()[i]);
			log.info("attachMapper.inster 결과 : "+result);			
		}
		return result;
	}
	
	@Override
	public int updateBoard(Board2VO vo) {
		log.info("service : board updateBoard()");
		int result = board2Mapper.updateBoard(vo);
		AttachVO[] attach = vo.getAttachVO();
		log.info(attach);
		if(attach != null) {
			result = attachMapper.delete(vo.getBoardId());
			log.info("attachMapper.delete 결과 : "+result);
			for(int i=0;i<vo.getAttachVO().length;i++) {
				result = attachMapper.insert(vo.getAttachVO()[i]);
				log.info("attachMapper.inster 결과 : "+result);			
			}			
		}
		return result;
	}

	@Override
	public List<Board2VO> selectList() {
		log.info("service : board selectList()");
		List<Board2VO> list = board2Mapper.selectList();
		return list;
	}

	@Override
	public List<Board2VO> selectByTitle(String title) {
		log.info("service : board selectByTitle()");
		List<Board2VO> list = board2Mapper.selectByTitle(title);
		return list;
	}

	@Override
	public List<Board2VO> selectByContent(String content) {
		log.info("service : board selectByContent()");
		List<Board2VO> list = board2Mapper.selectByContent(content);
		return list;
	}

	@Override
	public List<Board2VO> selectByMember(int member_id) {
		log.info("service : board selectByMember()");
		List<Board2VO> list = board2Mapper.selectByMember(member_id);
		return list;
	}



	@Override
	public int deleteBoard(int boardId) {
		log.info("service : board deleteBoard()");
		int result;
		List<ReplyVO> list = replyMapper.selectReplyBoard(boardId);
		for(int i = 0; i<list.size();i++) {
			result = rereplyMapper.deleteRereplyToReply(list.get(i).getReplyId());
		}
		result = replyMapper.deleteReplyByBoard(boardId);
		result = attachMapper.delete(boardId);
		result = board2Mapper.deleteBoard(boardId);
		return result;
	}

	@Override
	public Board2VO selectDetail(int boardId) {
		Board2VO vo = board2Mapper.selectDetail(boardId);
		vo.setAttachVO(attachMapper.selectByBoardId(boardId));
		return vo;
	}

	@Override
	public List<Board2VO> getPagingBoards(Pagination pagination) {
		log.info("getPagingBoards");
		List<Board2VO> list = board2Mapper.selectListByPagination(pagination);
		return list;
	}

	@Override
	public int getTotalCount() {
		log.info("getTotalCount()");
		return board2Mapper.selectTotalCount();
	}


}

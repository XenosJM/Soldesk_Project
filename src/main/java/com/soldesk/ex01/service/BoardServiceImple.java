package com.soldesk.ex01.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.RecommendVO;
import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.persistence.AttachMapper;
import com.soldesk.ex01.persistence.BoardMapper;
import com.soldesk.ex01.persistence.RecommendMapper;
import com.soldesk.ex01.persistence.ReplyMapper;
import com.soldesk.ex01.persistence.RereplyMapper;
import com.soldesk.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImple implements BoardService {

	@Autowired
	BoardMapper boardMapper;

	@Autowired
	AttachMapper attachMapper;

	@Autowired
	ReplyMapper replyMapper;

	@Autowired
	RereplyMapper rereplyMapper;
	
	@Autowired
	RecommendMapper recommendMapper;

	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int insertBoard(BoardVO vo) {
		log.info("service : insertBoard()");
		int result = boardMapper.insertBoard(vo);
		log.info("board2Mapper.insert 결과 : " + result);
		result = recommendMapper.insertRecommend(new RecommendVO());
		
		// result = attachMapper.insert(vo.getAttachVO());
		AttachVO[] attach = vo.getAttachVO();
		if (attach != null) {
			for (int i = 0; i < attach.length; i++) {
				result = attachMapper.insert(vo.getAttachVO()[i]);
			}
		}
		return result;
	}
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#vo.memberId == principal.username)")
	@Override
	public int updateBoard(BoardVO vo) {
		log.info("service : board updateBoard()");
		int result = boardMapper.updateBoard(vo);
		AttachVO[] attach = vo.getAttachVO();
		log.info(attach);
		if (attach != null) {
			result = attachMapper.delete(vo.getBoardId());
			log.info("attachMapper.delete 결과 : " + result);
			for (int i = 0; i < vo.getAttachVO().length; i++) {
				attach[i].setBoardId(vo.getBoardId());
				result = attachMapper.updateAttach(vo.getAttachVO()[i]);
				log.info("attachMapper.update 결과 : " + result);
			}
		}
		return result;
	}



	@Transactional
	@PreAuthorize("isAuthenticated() and ((#vo.memberId == principal.username) or hasRole('ROLE_MANAGER') or hasRole('ROLE_HEAD_MANAGER'))")
	@Override
	public int deleteBoard(BoardVO vo) {
		log.info("service : board deleteBoard()");
		int result;
		List<ReplyVO> list = replyMapper.selectReplyBoard(vo.getBoardId());
		for (int i = 0; i < list.size(); i++) {
			result = rereplyMapper.deleteRereplyToReply(list.get(i).getReplyId());
		}
		result = replyMapper.deleteReplyByBoard(vo.getBoardId());
		result = attachMapper.delete(vo.getBoardId());
		result = recommendMapper.deleteRecommend(vo.getBoardId());
		result = boardMapper.deleteBoard(vo.getBoardId());
		return result;
	}

	
	@Override
	public BoardVO selectDetail(int boardId) {
		BoardVO vo = boardMapper.selectDetail(boardId);
		RecommendVO revo = recommendMapper.selectRecommend(boardId);
		log.info(revo);
		vo.setRecommendVO(revo);
		log.info(vo);
		if (attachMapper.selectByBoardId(boardId) != null) {
			vo.setAttachVO(attachMapper.selectByBoardId(boardId));
		}
		return vo;
	}

	@Override
	public List<BoardVO> getPagingBoards(Pagination pagination/* int categoryId, int start, int end */) {
		log.info("getPagingBoards");
		List<BoardVO> list = boardMapper.selectListByPagination(/* categoryId,start,end */pagination);
		return list;
	}
	


	@Override
	public int getTotalCount(Pagination pagination) {
		log.info("getTotalCount()");
		return boardMapper.selectTotalCount(pagination);
	}
	
	@Override
	public int increaseRecommend(int boardId) {
		return boardMapper.increaseRecommend(boardId);
	}

	public List<BoardVO> selectListByRecommend(Pagination pagination){
		return boardMapper.selectListByRecommend(pagination);
	}
	
	public List<BoardVO> selectListByRecommendAll(Pagination pagintaion){
		return boardMapper.selectListByRecommendAll(pagintaion);
	}
	
	public int selectTotalCountByRecommend(Pagination pagination) {
		return boardMapper.selectTotalCountByRecommend(pagination);
	}
	
	public int selectTotalCountByRecommendAll(Pagination pagination) {
		return boardMapper.selectTotalCountByRecommendAll(pagination);
	}
}

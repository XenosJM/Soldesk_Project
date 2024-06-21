package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.persistence.AttachMapper;
import com.soldesk.ex01.persistence.BoardMapper;
import com.soldesk.ex01.persistence.ReplyMapper;
import com.soldesk.ex01.persistence.RereplyMapper;
import com.soldesk.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImple implements BoardService {

	@Autowired
	BoardMapper board2Mapper;

	@Autowired
	AttachMapper attachMapper;

	@Autowired
	ReplyMapper replyMapper;

	@Autowired
	RereplyMapper rereplyMapper;

	@Override
	public int insertBoard(BoardVO vo) {
		log.info("service : insertBoard()");
		int result = board2Mapper.insertBoard(vo);
		log.info("board2Mapper.insert 결과 : " + result);
		// result = attachMapper.insert(vo.getAttachVO());
		AttachVO[] attach = vo.getAttachVO();
		if (attach != null) {
			for (int i = 0; i < attach.length; i++) {
				result = attachMapper.insert(vo.getAttachVO()[i]);
			}
		}
		return result;
	}

	@Override
	public int updateBoard(BoardVO vo) {
		log.info("service : board updateBoard()");
		int result = board2Mapper.updateBoard(vo);
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

	@Override
	public List<BoardVO> selectList() {
		log.info("service : board selectList()");
		List<BoardVO> list = board2Mapper.selectList();
		return list;
	}

	@Override
	public List<BoardVO> selectByTitle(String title) {
		log.info("service : board selectByTitle()");
		List<BoardVO> list = board2Mapper.selectByTitle(title);
		return list;
	}

	@Override
	public List<BoardVO> selectByContent(String content) {
		log.info("service : board selectByContent()");
		List<BoardVO> list = board2Mapper.selectByContent(content);
		return list;
	}

	@Override
	public List<BoardVO> selectByMember(int member_id) {
		log.info("service : board selectByMember()");
		List<BoardVO> list = board2Mapper.selectByMember(member_id);
		return list;
	}

	@Override
	public int deleteBoard(int boardId) {
		log.info("service : board deleteBoard()");
		int result;
		List<ReplyVO> list = replyMapper.selectReplyBoard(boardId);
		for (int i = 0; i < list.size(); i++) {
			result = rereplyMapper.deleteRereplyToReply(list.get(i).getReplyId());
		}
		result = replyMapper.deleteReplyByBoard(boardId);
		result = attachMapper.delete(boardId);
		result = board2Mapper.deleteBoard(boardId);
		return result;
	}

	@Override
	public BoardVO selectDetail(int boardId) {
		BoardVO vo = board2Mapper.selectDetail(boardId);
		if (attachMapper.selectByBoardId(boardId) != null) {
			vo.setAttachVO(attachMapper.selectByBoardId(boardId));
		}
		return vo;
	}

	@Override
	public List<BoardVO> getPagingBoards(Pagination pagination/* int categoryId, int start, int end */) {
		log.info("getPagingBoards");
		List<BoardVO> list = board2Mapper.selectListByPagination(/* categoryId,start,end */pagination);
		return list;
	}

	@Override
	public int getTotalCount(int categoryId) {
		log.info("getTotalCount()");
		return board2Mapper.selectTotalCount(categoryId);
	}

	@Override
	public int recommendIncrease(int boardId) {
		return board2Mapper.recommendIncrease(boardId);
	}

}

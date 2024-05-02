package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.persistence.BoardMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImple implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;
	@Override
	public int insertBoard(BoardVO vo) {
		log.info("service : insertBoard()");
		int result = boardMapper.insertBoard(vo);
		return result;
	}

	@Override
	public List<BoardVO> selectList() {
		log.info("service : board selectList()");
		List<BoardVO> list = boardMapper.selectList();
		return list;
	}

	@Override
	public List<BoardVO> selectByTitle(String title) {
		log.info("service : board selectByTitle()");
		List<BoardVO> list = boardMapper.selectByTitle(title);
		return list;
	}

	@Override
	public List<BoardVO> selectByContent(String content) {
		log.info("service : board selectByContent()");
		List<BoardVO> list = boardMapper.selectByContent(content);
		return list;
	}

	@Override
	public List<BoardVO> selectByMember(int member_id) {
		log.info("service : board selectByMember()");
		List<BoardVO> list = boardMapper.selectByMember(member_id);
		return list;
	}

	@Override
	public int updateBoard(BoardVO vo) {
		log.info("service : board updateBoard()");
		int result = boardMapper.updateBoard(vo);
		return result;
	}

	@Override
	public int deleteBoard(int boardId) {
		log.info("service : board deleteBoard()");
		int result = boardMapper.deleteBoard(boardId);
		return result;
	}

	@Override
	public BoardVO selectDetail(int boardId) {
		BoardVO vo = boardMapper.selectDetail(boardId);
		return vo;
	}

}

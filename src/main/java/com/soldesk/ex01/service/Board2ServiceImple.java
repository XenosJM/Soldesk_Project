package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.persistence.AttachMapper;
import com.soldesk.ex01.persistence.Board2Mapper;


import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class Board2ServiceImple implements Board2Service {
	
	@Autowired
	Board2Mapper board2Mapper;
	
	@Autowired
	AttachMapper attachMapper;
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
	public int updateBoard(Board2VO vo) {
		log.info("service : board updateBoard()");
		int result = board2Mapper.updateBoard(vo);
		return result;
	}

	@Override
	public int deleteBoard(int boardId) {
		log.info("service : board deleteBoard()");
		int result = board2Mapper.deleteBoard(boardId);
		return result;
	}

	@Override
	public Board2VO selectDetail(int boardId) {
		Board2VO vo = board2Mapper.selectDetail(boardId);
		vo.setAttachVO(attachMapper.selectByBoardId(boardId));
		return vo;
	}



}

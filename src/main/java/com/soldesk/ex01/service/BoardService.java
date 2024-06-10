package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.domain.BoardVO;

public interface BoardService {
	int insertBoard(BoardVO vo);
	List<BoardVO> selectList();
	List<BoardVO> selectByTitle(String title);
	List<BoardVO> selectByContent(String content);
	List<BoardVO> selectByMember(int member_id);
	BoardVO selectDetail(int boardId);
	int updateBoard(BoardVO vo);
	int deleteBoard(int boardId);
	int updateBoard(Board2VO vo);	
}

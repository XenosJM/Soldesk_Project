package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.util.Pagination;

public interface Board2Service {
	int insertBoard(Board2VO vo);
	List<Board2VO> selectList();
	List<Board2VO> selectByTitle(String title);
	List<Board2VO> selectByContent(String content);
	List<Board2VO> selectByMember(int member_id);
	Board2VO selectDetail(int boardId);
	int updateBoard(Board2VO vo);
	int deleteBoard(int boardId);	
	List<Board2VO> getPagingBoards(Pagination pagination);
	int getTotalCount();

}

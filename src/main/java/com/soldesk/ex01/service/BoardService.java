package com.soldesk.ex01.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.util.Pagination;

public interface BoardService {
	int insertBoard(BoardVO vo);
	BoardVO selectDetail(int boardId);
	int updateBoard(BoardVO vo);
	int deleteBoard(BoardVO vo);	
	List<BoardVO> getPagingBoards(Pagination pagination/*@Param("categoryId")int categoryId, @Param("start")int start,@Param("end")int end*/);
	int getTotalCount(Pagination pagination);
	int increaseRecommend(int boardId);
	List<BoardVO> selectListByRecommend(Pagination pagination);
	List<BoardVO> selectListByRecommendAll(Pagination pagintaion);
	int selectTotalCountByRecommend(Pagination pagination);
	int selectTotalCountByRecommendAll(Pagination pagination);
}

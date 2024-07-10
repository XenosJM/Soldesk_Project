package com.soldesk.ex01.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.util.Pagination;

public interface BoardService {
	int insertBoard(BoardVO vo);
	List<BoardVO> selectList();
	List<BoardVO> selectByTitle(@Param("boardTitle") String title,@Param("categoryId")int categoryId,@Param("pagination")Pagination pagination);
	List<BoardVO> selectByContent(@Param("boardContent")String content,@Param("categoryId")int categoryId,@Param("pagination")Pagination pagination);
	List<BoardVO> selectByMember(int member_id);
	BoardVO selectDetail(int boardId);
	int updateBoard(BoardVO vo);
	int deleteBoard(int boardId);	
	List<BoardVO> getPagingBoards(Pagination pagination/*@Param("categoryId")int categoryId, @Param("start")int start,@Param("end")int end*/);
	int getTotalCount(Pagination pagination);
	int searchTotalCountByTitle(@Param("categoryId")int categoryId, @Param("boardTitle")String title);
	int searchTotalCountByContent(@Param("categoryId")int categoryId, @Param("boardContent")String content);
	int increaseRecommend(int boardId);
}

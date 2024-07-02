package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.BoardVO;

import com.soldesk.ex01.util.Pagination;

@Mapper
public interface BoardMapper {
	int insertBoard(BoardVO vo);
	List<BoardVO> selectList();
	List<BoardVO> selectByTitle(@Param("boardTitle")String title,@Param("categoryId")int categoryId,@Param("pagination")Pagination pagination);
	List<BoardVO> selectByContent(@Param("boardContent")String content,@Param("categoryId")int categoryId,@Param("pagination")Pagination pagination);
	List<BoardVO> selectByMember(int memberId);
	BoardVO selectDetail(int boardId);
	int updateBoard(BoardVO vo);
	int deleteBoard(int boardId);	
	List<BoardVO> selectListByPagination(Pagination pagination/*@Param("categoryId") int categoryId,@Param("start")int start,@Param("end")int end*/);
	int selectTotalCount(int categoryId);
	int increaseReplyCount(int boardId);
	int decreaseReplyCount(int boardId);
	int recommendIncrease(int boardId);
	int searchTotalCountByTitle(@Param("categoryId")int categoryId, @Param("boardTitle")String title);
	int searchTotalCountByContent(@Param("categoryId")int categoryId, @Param("boardContent")String content);
}

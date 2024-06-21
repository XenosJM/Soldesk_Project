package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.util.Pagination;

@Mapper
public interface Board2Mapper {
	int insertBoard(Board2VO vo);
	List<Board2VO> selectList();
	List<Board2VO> selectByTitle(String title);
	List<Board2VO> selectByContent(String content);
	List<Board2VO> selectByMember(int memberId);
	Board2VO selectDetail(int boardId);
	int updateBoard(Board2VO vo);
	int deleteBoard(int boardId);	
	List<Board2VO> selectListByPagination(Pagination pagination/*@Param("categoryId") int categoryId,@Param("start")int start,@Param("end")int end*/);
	int selectTotalCount(int categoryId);
	int increaseReplyCount(int boardId);
	int decreaseReplyCount(int boardId);
	int recommendIncrease(int boardId);
}

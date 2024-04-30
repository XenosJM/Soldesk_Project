package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.BoardVO;

@Mapper
public interface BoardMapper {
	int insertBoard(BoardVO vo);
	List<BoardVO> selectList();
	List<BoardVO> selectByTitle(BoardVO vo);
	List<BoardVO> selectByMember(BoardVO vo);
	int updateBoard(BoardVO vo);
	int deleteBoard(int boardId);	
}

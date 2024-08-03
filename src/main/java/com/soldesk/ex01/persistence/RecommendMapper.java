package com.soldesk.ex01.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.RecommendVO;

@Mapper
public interface RecommendMapper {
	int insertRecommend(RecommendVO vo);
	
	
	int deleteRecommend(int boardId);
	RecommendVO selectRecommend(int boardId);
	int updateRecommendMember(RecommendVO recommendVO/*@Param("boardId")int boardId, @Param("recommendMemberString")String recommendMemberString*/);
	
}

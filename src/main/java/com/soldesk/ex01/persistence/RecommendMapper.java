package com.soldesk.ex01.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.RecommendVO;

@Mapper
public interface RecommendMapper {
	int insertRecommend(RecommendVO vo);
	int increaseRecommend(int boardId);
	int increaseDecommend(int boardId);
	int deleteRecommend(int boardId);
	RecommendVO selectRecommend(int boardId);
	int updateRecommendMember(int boardId, String recommendMember);
	
}

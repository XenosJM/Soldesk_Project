package com.soldesk.ex01.service;

import com.soldesk.ex01.domain.RecommendVO;

public interface RecommendService {
	int insertRecommend(RecommendVO vo);
	int deleteRecommend(int boardId);
	RecommendVO selectRecommend(int boardId);
	int updateRecommendMember(RecommendVO vo);
	boolean checkRecommend(int boardId, String meberId);
}

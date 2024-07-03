package com.soldesk.ex01.service;

import com.soldesk.ex01.domain.RecommendVO;

public interface RecommendService {
	int insertRecommend(RecommendVO vo);
	int increaseRecommend(int boardId);
	int increaseDecommend(int boardId);
	int deleteRecommend(int boardId);
	RecommendVO selectRecommend(int boardId);
}

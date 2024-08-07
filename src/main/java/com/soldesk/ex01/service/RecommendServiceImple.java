package com.soldesk.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.RecommendVO;
import com.soldesk.ex01.persistence.RecommendMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RecommendServiceImple implements RecommendService {

	@Autowired
	RecommendMapper recommendMapper;

	@Override
	public int insertRecommend(RecommendVO vo) {
		return recommendMapper.insertRecommend(vo);
	}

	@Override
	public int deleteRecommend(int boardId) {
		return recommendMapper.deleteRecommend(boardId);
	}

	@Override
	public RecommendVO selectRecommend(int boardId) {
		return recommendMapper.selectRecommend(boardId);
	}

	public int updateRecommendMember(RecommendVO vo) {
		vo.setRecommendMemberString(vo.getRecommendMemberAsString());
		int result = recommendMapper.updateRecommendMember(vo);
		return result;
	}

	@Override
	public boolean checkRecommend(int boardId, String meberId) {
		RecommendVO vo = recommendMapper.selectRecommend(boardId);
		boolean result;
		if (vo.getRecommendMemberString() != null) {
			result = vo.getRecommendMemberString().contains(meberId);
		} else {
			result = false;
		}
		return result;
	}

}

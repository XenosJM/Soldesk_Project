package com.soldesk.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soldesk.ex01.domain.RecommendVO;
import com.soldesk.ex01.service.RecommendService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/recommend")
@Log4j
public class RecommendController {
	@Autowired
	private RecommendService recommendService;
	

	public int insertRecommend(RecommendVO vo) {
		return recommendService.insertRecommend(vo);
	}
	
	@PostMapping("/recommend")
	@ResponseBody
	public ResponseEntity<Integer> increaseRecommend(int boardId){
		int result = recommendService.increaseRecommend(boardId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@PostMapping("/decommend")
	@ResponseBody
	public ResponseEntity<Integer> increaseDecommend(int boardId){
		int result = recommendService.increaseDecommend(boardId);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	public int deleteRecommend(int boardId) {
		return recommendService.deleteRecommend(boardId);
	}
	
	
	
	
	
	
}

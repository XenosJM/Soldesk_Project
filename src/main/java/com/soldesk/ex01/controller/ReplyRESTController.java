package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.service.ReplyService;

import lombok.extern.log4j.Log4j;

@RestController 
@RequestMapping(value="/reply")
@Log4j
public class ReplyRESTController {
	
	@Autowired
	private ReplyService replyService;
	
	@PostMapping
	public ResponseEntity<Integer> createReply(@RequestBody ReplyVO replyVO){
		log.info("reply controller : createReply()");
		int result = replyService.insertReply(replyVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/{boardId}")
	public ResponseEntity<List<ReplyVO>> readAllReply(@PathVariable("boardId") int boardId){
		log.info("reply controller : readAllReply()");
		log.info("boardId = "+boardId );
		List<ReplyVO> list = replyService.selectReplyBoard(boardId);
		return new ResponseEntity<List<ReplyVO>>(list,HttpStatus.OK);
	}
	
	@PutMapping("/{replyId}")
	public ResponseEntity<Integer> updateReply(@PathVariable("replyId")int replyId, @RequestBody String replyContent){
		log.info("reply controller : updateReply()");
		log.info("replyId = " +replyId);
		log.info("replyContent : "+replyContent);
		int result = replyService.updateReply(replyId,replyContent);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	@DeleteMapping("/{replyId}/{boardId}")
	public ResponseEntity<Integer> deleteReply(@PathVariable("replyId")int replyId){
		log.info("reply controller : deleteReply");
		log.info("replyId = "+replyId);
		int result = replyService.deleteReply(replyId);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
}

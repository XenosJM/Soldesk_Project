package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.RereplyVO;
import com.soldesk.ex01.service.RereplyService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/rereply")
@Log4j
public class RereplyRESTController {
	
	@Autowired
	private RereplyService rereplyService;
	
	@PostMapping
	public ResponseEntity<Integer> createRereply(@RequestBody RereplyVO rereplyvo){
		log.info("reply controller : createRereply()");
		int result = rereplyService.insertRereply(rereplyvo);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	@GetMapping("/{replyId}")
	public ResponseEntity<List<RereplyVO>> readAllRereply(@PathVariable("replyId") Integer replyId){
		log.info("rereply controller : readAllRereply()");
		log.info("replyId = "+replyId);
		List<RereplyVO> list = rereplyService.selectRereply(replyId);
		return new ResponseEntity<List<RereplyVO>>(list,HttpStatus.OK);
	}
	
	@PutMapping("/{rereplyId}")
	public ResponseEntity<Integer> updateRereply(@PathVariable("rereplyId")int rereplyId,@RequestBody String rereplyContent){
		log.info("rereply controller : updateRereply()");
		log.info("rereplyId = "+rereplyId);
		log.info("rereplyContent = "+rereplyContent);
		int result = rereplyService.updateRereply(rereplyId, rereplyContent);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	@DeleteMapping("/{rereplyId}")
	public ResponseEntity<Integer> deleteRereply(@PathVariable("rereplyId")int rereplyId){
		log.info("rereply contorller: deleteRereply()");
		log.info("rereplyId = "+rereplyId);
		int result = rereplyService.deleteRereply(rereplyId);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{replyId}/{boardId}")
	public ResponseEntity<Integer> deleteRereplyToReply(@PathVariable("replyId")int replyId, @PathVariable("boardId")int boardId){
		log.info("rereply contorller: deleteRereplyToReply()");
		log.info("replyId = "+replyId);
		int result = rereplyService.deleteRereplyToReply(replyId);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
}

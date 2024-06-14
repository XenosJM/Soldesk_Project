package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;
import com.soldesk.ex01.service.FriendService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/friend")
@Log4j
public class FriendController {
	
	@Autowired
	private FriendService friendService;
	
	@Transactional
	@PostMapping("/send")
	public ResponseEntity<Integer> sendRequest(@RequestBody RequestVO requestVO ){
		log.info("sendRequest()");
		int result = friendService.insertRequest(requestVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getRequest/{memberId}")
	public ResponseEntity<List<RequestVO>> getRequestList(@PathVariable("memberId") String memberId){
		log.info("getRequestList()");
		List<RequestVO> requestList = friendService.sendList(memberId);
		log.info(requestList);
		return new ResponseEntity<List<RequestVO>>(requestList, HttpStatus.OK);
	}
	
	@PutMapping("/requestState/{requestId}")
	public ResponseEntity<Integer> requestStateChange(@PathVariable("requestId") int requestId, @RequestParam("requestState") String requestState){
		log.info("requestStateChange()");
		int result = friendService.requestStateChange(requestId, requestState);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/cancel/{requestId}")
	public ResponseEntity<Integer> cancelRequest(@PathVariable("requestId") int requestId){
		log.info("cancelRequest()");
		int result = friendService.cancelRequest(requestId);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	// ------------------------------------------- receive
	
	@PostMapping("/receive")
	public ResponseEntity<Integer> receiveRequest(@RequestBody ReceiveVO receiveVO ){
		log.info("receiveRequest()");
		int result = friendService.insertReceive(receiveVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getReceive/{memberId}")
	public ResponseEntity<List<ReceiveVO>> getReceiveList(@PathVariable("memberId") String memberId){
		log.info("getReceiveList()");
		List<ReceiveVO> receiveList = friendService.receiveList(memberId);
		log.info(receiveList);
		return new ResponseEntity<List<ReceiveVO>>(receiveList, HttpStatus.OK);
	}
	
	@PostMapping("/receiveState")
	public ResponseEntity<Integer> receiveStateChange(@RequestParam("receiveId") int receiveId, @RequestParam("receiveState") String receiveState){
		log.info("receiveStateChange()");
		int result = friendService.receiveStateChange(receiveId, receiveState);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/reject/{receiveId}")
	public ResponseEntity<Integer> rejentRequest(@PathVariable("receiveId") int receiveId){
		log.info("rejentRequest()");
		int result = friendService.rejectRequest(receiveId);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	// ------------------------------------ friend
	
	@PostMapping("/insert")
	public ResponseEntity<Integer> insertFriend(@RequestBody FriendVO friendVO ){
		log.info("insertFriend()");
		int result = friendService.insertFriend(friendVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getFriend/{memberId}")
	public ResponseEntity<List<FriendVO>> getFriendList(@PathVariable("memberId") String memberId){
		log.info("getFriendList()");
		List<FriendVO> friendList = friendService.friendList(memberId);
		return new ResponseEntity<List<FriendVO>>(friendList, HttpStatus.OK);
	}
	
	@PostMapping("/friendState")
	public ResponseEntity<Integer> friendStateChange(@RequestParam("memberId") String memberId, @RequestParam("friendState") String friendState){
		log.info("friendStateChange()");
		int result = friendService.friendStateChange(memberId, friendState);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete/{friendshipId}")
	public ResponseEntity<Integer> deleteFriend(@PathVariable("friendshipId") int friendshipId){
		log.info("deleteFriend()");
		int result = friendService.deleteFriend(friendshipId);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

package com.soldesk.ex01.controller;

import java.util.List;
import java.util.Map;

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
	private FriendService friend;
	
	@PostMapping("/insert")
	public ResponseEntity<Integer> insertFriend(@RequestBody FriendVO friendVO ){
		log.info("insertFriend()");
		int result = friend.insertFriend(friendVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getFriend/{memberId}")
	public ResponseEntity<List<FriendVO>> getFriendList(@PathVariable("memberId") String memberId){
		log.info("getFriendList()");
		List<FriendVO> friendList = friend.friendList(memberId);
		return new ResponseEntity<List<FriendVO>>(friendList, HttpStatus.OK);
	}
	
	@PostMapping("/friendState")
	public ResponseEntity<Integer> friendStateChange(@RequestParam("memberId") String memberId, @RequestParam("friendState") String friendState){
		log.info("friendStateChange()");
		int result = friend.friendStateChange(memberId, friendState);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteFriend(@RequestBody FriendVO friendVO){
		log.info("deleteFriend()");
		int result = friend.deleteFriend(friendVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.ChatVO;
import com.soldesk.ex01.service.ChatService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/chat")
@Log4j
public class ChatRestContoller {
	
	@Autowired
	private ChatService chat;
	
	@PostMapping("/record")
	public ResponseEntity<Integer> recordChat(@RequestBody ChatVO chatVO) {
		int result = chat.recordChat(chatVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/privateRecord")
	public ResponseEntity<List<ChatVO>> getPrivateChatRecord(@RequestBody ChatVO chatVO){
		List<ChatVO> list = chat.getPrivateChatRecord(chatVO);
		return new ResponseEntity<List<ChatVO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/groupRecord")
	public ResponseEntity<List<ChatVO>> getGroupChatRecord(@RequestBody ChatVO chatVO){
		List<ChatVO> list = chat.getGroupChatRecord(chatVO);
		return new ResponseEntity<List<ChatVO>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteChatRecord(@RequestBody ChatVO chatVO){
		int result = chat.deleteChatRecord(chatVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/remove")
	public ResponseEntity<Integer> removeChatRecord(int chatGroupId){
		int result = chat.removeChatRecord(chatGroupId);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

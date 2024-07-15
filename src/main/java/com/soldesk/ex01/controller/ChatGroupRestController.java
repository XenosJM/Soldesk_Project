package com.soldesk.ex01.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.ChatGroupVO;
import com.soldesk.ex01.service.ChatGroupService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/group")
@Log4j
public class ChatGroupRestController {
	
	@Autowired
	private ChatGroupService group;
	
	@PostMapping("/create")
	public ResponseEntity<Integer> createChatGroup(ChatGroupVO groupVO){
		int result = group.createGroup(groupVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/groupList")
	public ResponseEntity<List<ChatGroupVO>> getGroupList(String memberId){
		List<ChatGroupVO> list = group.getGroupList(memberId);
		return new ResponseEntity<List<ChatGroupVO>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/changeTitle")
	public ResponseEntity<Integer> changeGroupTitle(ChatGroupVO groupVO){
		int result = group.updateGroupTitle(groupVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteGroup(int chatGroupId){
		int result = group.deleteGroup(chatGroupId);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/invite")
	public ResponseEntity<Integer> inviteMember(@Param("chatGroupId") int chatGroupId,@Param("memberId") String memberId){
		int result = group.inviteMember(chatGroupId, memberId);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

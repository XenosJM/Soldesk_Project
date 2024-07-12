package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;
import com.soldesk.ex01.service.ReceiveService;
import com.soldesk.ex01.service.RequestService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/receive")
@Log4j
public class ReceiveRestController {
	
	@Autowired
	private ReceiveService receive;
	
	@Autowired
	private RequestService request;
	
	@PostMapping("/insert")
	public ResponseEntity<Integer> receiveRequest(@RequestBody ReceiveVO receiveVO ){
		log.info("receiveRequest()");
		int result = receive.insertReceive(receiveVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getReceive/{memberId}")
	public ResponseEntity<List<ReceiveVO>> getReceiveList(@PathVariable("memberId") String memberId){
		log.info("getReceiveList()");
		List<ReceiveVO> receiveList = receive.receiveList(memberId);
		log.info(receiveList);
		return new ResponseEntity<List<ReceiveVO>>(receiveList, HttpStatus.OK);
	}
	
	@PostMapping("/receiveState")
	public ResponseEntity<Integer> receiveStateChange(@RequestBody ReceiveVO receiveVO/*@PathVariable("receiveId") int receiveId, @RequestParam("receiveState") String receiveState*/){
		log.info("receiveStateChange()");
		log.info(receiveVO);
		int result = receive.receiveStateChange(receiveVO.getReceiveId(), receiveVO.getReceiveState());
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/reject/{receiveId}")
	public ResponseEntity<Integer> rejentRequest(@PathVariable("receiveId") int receiveId){
		log.info("rejentRequest()");
		int result = receive.rejectRequest(receiveId);
		// 거절이 성공적일 경우
		if(result == 1) {
			ReceiveVO receiveVO = receive.getByReceiveId(receiveId);
			RequestVO requestVO = request.getRequestByReceiverId(receiveVO.getMemberId());
			// 해당 보낸 요청의 requestId 값을 리턴
			result = requestVO.getRequestId();
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

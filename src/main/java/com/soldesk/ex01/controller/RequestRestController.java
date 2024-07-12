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
@RequestMapping(value = "/request")
@Log4j
public class RequestRestController {
	
	@Autowired
	private RequestService request;
	
	@Autowired
	private ReceiveService receive;
	
	@PostMapping("/send")
	public ResponseEntity<Integer> sendRequest(@RequestBody RequestVO requestVO ){
		log.info("sendRequest()");
		int result = request.insertRequest(requestVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getRequest/{memberId}")
	public ResponseEntity<List<RequestVO>> getRequestList(@PathVariable("memberId") String memberId){
		log.info("getRequestList()");
		List<RequestVO> requestList = request.sendList(memberId);
		log.info(requestList);
		return new ResponseEntity<List<RequestVO>>(requestList, HttpStatus.OK);
	}
	
	@PostMapping("/requestState")
	public ResponseEntity<Integer> requestStateChange(@RequestBody RequestVO requestVO /* @PathVariable ("requestId") int requestId, @RequestParam("requestState") String requestState */){
		log.info("requestStateChange()");
		log.info(requestVO);
		int result = request.requestStateChange(requestVO.getRequestId(), requestVO.getRequestState());
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/cancel/{requestId}")
	public ResponseEntity<Integer> cancelRequest(@PathVariable("requestId") int requestId){
		log.info("cancelRequest()");
		int result = request.cancelRequest(requestId);
		
		if(result == 1) {
			RequestVO requestVO = request.getByRequestId(requestId);
			ReceiveVO receiveVO = receive.getByRequesterId(requestVO.getMemberId());
			
			result = receiveVO.getReceiveId();
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

}

package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.RoleVO;
import com.soldesk.ex01.service.RoleService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/role")
@Log4j
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
//	@PostMapping("/regist")
//	public ResponseEntity<Integer> createManager(@RequestBody RoleVO managerVO) {
//		log.info("registManager()");
//		int result = roleService.createManager(managerVO);
//		log.info(result + "행 삽입");
//		return new ResponseEntity<Integer>(result, HttpStatus.OK);
//	}	
	
	// TODO 비동기로 select를 누르면 div를 추가해 모든 멤버 리스트를 가져오고 input 검색 창을 추가하여
	// 검색창에 검색어를 입력할때마다 그에 맞는 이용자가 뜨고 선택된 멤버에게 관리자 권한을 주거나 취소시킬수있다.
	// 권한 주기를 클릭했을경우 게시판 목록(카테고리)이 뜨고 이를 선택하면 권한주기 컨펌창을 띄워 체크한다.  
	

	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteManager(int managerId) {
		log.info("deleteManager()");
		int result = roleService.deleteManager(managerId);
		log.info(result + "행 삭제");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
//	@GetMapping("/selectAll")
//	public ResponseEntity<List<RoleVO>> getAllManager() {
//		log.info("getAllManager()");
//		List<RoleVO> managerList = managerService.getAllManager();
//		return new ResponseEntity<>(managerList, HttpStatus.OK);
//	}
}

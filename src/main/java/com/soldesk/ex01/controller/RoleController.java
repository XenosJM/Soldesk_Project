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
	
	@PostMapping("/regist")
	public ResponseEntity<Integer> createManager(@RequestBody RoleVO managerVO) {
		log.info("registManager()");
		int result = roleService.createManager(managerVO);
		log.info(result + "�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}	
	
	// TODO �񵿱�� select�� ������ div�� �߰��� ��� ��� ����Ʈ�� �������� input �˻� â�� �߰��Ͽ�
	// �˻�â�� �˻�� �Է��Ҷ����� �׿� �´� �̿��ڰ� �߰� ���õ� ������� ������ ������ �ְų� ��ҽ�ų���ִ�.
	// ���� �ֱ⸦ Ŭ��������� �Խ��� ���(ī�װ�)�� �߰� �̸� �����ϸ� �����ֱ� ����â�� ��� üũ�Ѵ�.  
	

	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteManager(int managerId) {
		log.info("deleteManager()");
		int result = roleService.deleteManager(managerId);
		log.info(result + "�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
//	@GetMapping("/selectAll")
//	public ResponseEntity<List<RoleVO>> getAllManager() {
//		log.info("getAllManager()");
//		List<RoleVO> managerList = managerService.getAllManager();
//		return new ResponseEntity<>(managerList, HttpStatus.OK);
//	}
}

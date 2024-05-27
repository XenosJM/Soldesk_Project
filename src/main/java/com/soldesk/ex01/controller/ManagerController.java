package com.soldesk.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.ManagerVO;
import com.soldesk.ex01.service.ManagerService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/manager")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/regist")
	public ResponseEntity<Integer> createManager(@RequestBody ManagerVO managerVO) {
		log.info("registManager()");
		int result = managerService.createManager(managerVO);
		log.info(result + "�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}	
	
	// TODO �񵿱�� select�� ������ div�� �߰��� ��� ��� ����Ʈ�� �������� input �˻� â�� �߰��Ͽ�
	// �˻�â�� �˻�� �Է��Ҷ����� �׿� �´� �̿��ڰ� �߰� ���õ� ������� ������ ������ �ְų� ��ҽ�ų���ִ�.
	// ���� �ֱ⸦ Ŭ��������� �Խ��� ���(ī�װ�)�� �߰� �̸� �����ϸ� �����ֱ� ����â�� ��� üũ�Ѵ�.  
	
	@PostMapping("/modify")
	public String modifyManager(ManagerVO managerVO) {
		log.info("modifyManager()");
		int result = managerService.changeManager(managerVO);
		log.info(result + "�� ����");
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String deleteManager(int managerId) {
		log.info("deleteManager()");
		int result = managerService.deleteManager(managerId);
		log.info(result + "�� ����");
		return "redirect:/";
	}
}

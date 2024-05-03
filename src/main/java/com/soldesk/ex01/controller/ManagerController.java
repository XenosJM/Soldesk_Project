package com.soldesk.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.ManagerVO;
import com.soldesk.ex01.service.ManagerService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/manager")
@Log4j
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/regist")
	public String createManager(ManagerVO managerVO, RedirectAttributes reAttr) {
		log.info("registManager()");
		int result = managerService.createManager(managerVO);
		log.info(result + "�� ����");
		return "redirect:/";
	}
	
	@GetMapping("/select")
	@ResponseBody
	public void getAllManager(Model model, ManagerVO managerVO) {
		log.info("getAllManager()");
		
		
	}	
	
	// TODO �񵿱�� select�� ������ div�� �߰��� ��� ��� ����Ʈ�� �������� input �˻� â�� �߰��Ͽ�
	// �˻�â�� �˻�� �Է��Ҷ����� �׿� �´� �̿��ڰ� �߰� ���õ� ������� ������ ������ �ְų� ��ҽ�ų���ִ�.
	// ���� �ֱ⸦ Ŭ��������� �Խ��� ���(ī�װ�)�� �߰� �̸� �����ϸ� �����ֱ� ����â�� ��� üũ�Ѵ�.  
	
	@PostMapping("/modify")
	public String modifyManager(ManagerVO managerVO, RedirectAttributes reAttr) {
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

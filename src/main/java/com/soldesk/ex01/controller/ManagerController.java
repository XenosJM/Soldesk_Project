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
		log.info(result + "행 삽입");
		return "redirect:/";
	}
	
	@GetMapping("/select")
	@ResponseBody
	public void getAllManager(Model model, ManagerVO managerVO) {
		log.info("getAllManager()");
		
		
	}	
	
	// TODO 비동기로 select를 누르면 div를 추가해 모든 멤버 리스트를 가져오고 input 검색 창을 추가하여
	// 검색창에 검색어를 입력할때마다 그에 맞는 이용자가 뜨고 선택된 멤버에게 관리자 권한을 주거나 취소시킬수있다.
	// 권한 주기를 클릭했을경우 게시판 목록(카테고리)이 뜨고 이를 선택하면 권한주기 컨펌창을 띄워 체크한다.  
	
	@PostMapping("/modify")
	public String modifyManager(ManagerVO managerVO, RedirectAttributes reAttr) {
		log.info("modifyManager()");
		int result = managerService.changeManager(managerVO);
		log.info(result + "행 수정");
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String deleteManager(int managerId) {
		log.info("deleteManager()");
		int result = managerService.deleteManager(managerId);
		log.info(result + "행 삭제");
		return "redirect:/";
	}
}

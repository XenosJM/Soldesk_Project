package com.soldesk.ex01.controller;

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.ManagerVO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.CategoryService;
import com.soldesk.ex01.service.ManagerService;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/member")
@Log4j
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/regist")
	public void registerGet() {
		log.info("registerGet()");
	}
	
	@PostMapping("/regist")
	public String registerPost(MemberVO memberVO, RedirectAttributes reAttr) {
		log.info("registerPOST()");
		log.info("memberVO = " + memberVO.toString());
		int result = memberService.createMember(memberVO);
		log.info(result + "행 등록");
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public void detailGet(Model model, Integer memberId) {
		log.info("detailGet()");
		MemberVO memberVO = new MemberVO(); 
		memberVO = memberService.getMemberById(memberId);
		model.addAttribute("memberVO", memberVO);
	}
	
	@GetMapping("/update")
	public void updateGet() {
		log.info("updateGet()");
	}
	
	@PostMapping("/modify")
	public String updatePost(MemberVO memberVO, RedirectAttributes reAttr) {
		log.info("updatePost()");
		log.info("memberVO = " + memberVO.toString());
		int result = memberService.updateMember(memberVO);
		log.info(result + "행 수정");
		return "redirect:/detail";
	}
	
	@PostMapping("/delete")
	public String deletePost(Integer memberId) {
		log.info("delete()");
		int result = memberService.deleteMember(memberId);
		log.info(result + "행 삭제");
		return "redirect:/";
	}
	
	@PostMapping("/check")
	public String memberCheck(String memberName, String memberPassword, RedirectAttributes reAttr) {
		log.info("memberCheck()");
		MemberVO memberVO = new MemberVO();
		memberVO = memberService.memberCheck(memberName);
		if(memberVO != null && memberPassword.equals(memberVO.getMemberPassword())) {
			if(memberVO.getManagerId() != 0) {
				//TODO 일단 쿠키 이용할 것
				reAttr.addAttribute("alert", "관리자님 어서오세요.");
				return "redirect:/manager";
			} else {
				String member = memberVO.getMemberName();
				reAttr.addAttribute("alert", member + "님 로그인 되셨습니다.");
				return "redirect:/";
			}
		}
		reAttr.addAttribute("alert", "아이디 또는 비밀번호를 확인해주세요");
		return "redirect:/";
	}
	
	
}






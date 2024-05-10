package com.soldesk.ex01.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/member")
@Log4j
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/regist")
	public void registerGet() {
		log.info("registerGet()");
	}
	
	@PostMapping("/regist")
	public String registerPost(MemberVO memberVO, RedirectAttributes reAttr) {
		log.info("registerPOST()");
		log.info("memberVO = " + memberVO.toString());
		reAttr.addAttribute("alert", memberVO.getMemberId() + "님 회원가입을 환엽합니다.");
		int result = memberService.createMember(memberVO);
		log.info(result + "행 등록");
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public void detailGet(Model model, HttpServletRequest req) {
		log.info("detailGet()");
		MemberVO memberVO = new MemberVO();
		HttpSession session = req.getSession();
		int memberNum = (int) session.getAttribute("memberNum");
		memberVO = memberService.getMemberById(memberNum);
		log.info(memberVO);
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
	public String deletePost(Integer memberNum) {
		log.info("delete()");
		int result = memberService.deleteMember(memberNum);
		log.info(result + "행 삭제");
		return "redirect:/";
	}
	
	@PostMapping("/check")
	public String memberCheck(String memberId, String memberPassword, HttpServletRequest req) {
		log.info("memberCheck()");
		MemberVO memberVO = new MemberVO();
		memberVO = memberService.memberCheck(memberId);
		if(memberVO != null && memberPassword.equals(memberVO.getMemberPassword())) {
			if(memberVO.getManagerNum() != 0) {
				log.info(memberVO.getMemberNum());
				HttpSession session = req.getSession();
				session.setAttribute("memberNum", memberVO.getMemberNum());
				session.setAttribute("managerNum", memberVO.getManagerNum());
//				session.setAttribute("memberName", memberVO.getMemberName());
				return "redirect:/";
			} else {
				HttpSession session = req.getSession();
				log.info(memberVO.getMemberNum());
				session.setAttribute("memberNum", memberVO.getMemberNum());
				log.info(session.getAttribute("memberNum"));
//				session.setAttribute("memberName", memberVO.getMemberName());
				return "redirect:/";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/checkout")
	public String memberCheckout(HttpServletRequest req) {
		log.info("memberCheckout()");
		HttpSession session = req.getSession();
		session.removeAttribute("memberNum");

		return "redirect:/";
	}
	
	@GetMapping("/checkId")
	public MemberVO checkId(String memberId) {
		log.info("checkId()");
		int result;
		MemberVO memberVO = new MemberVO();
		return memberService.checkId(memberId);
	}
	
}






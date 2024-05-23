package com.soldesk.ex01.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/member")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	
	@PostMapping("/regist")
	public ResponseEntity<Integer> joinMember(@RequestBody Map<String, String> res) {
		log.info("registerPOST()");
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(res.get("memberId"));
		memberVO.setMemberPassword(res.get("memberPassword"));
		memberVO.setMemberEmail(res.get("memberEmail"));
		log.info("memberVO = " + memberVO.toString());
		int result = memberService.createMember(memberVO);
		log.info(result + "행 등록");
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	@PostMapping("/modify")
	public ResponseEntity<Integer> updatePost(MemberVO memberVO, RedirectAttributes reAttr) {
		log.info("updatePost()");
		log.info("memberVO = " + memberVO.toString());
		int result = memberService.updateMember(memberVO);
		log.info(result + "행 수정");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public String deletePost(String memberId) {
		log.info("delete()");
		int result = memberService.deleteMember(memberId);
		log.info(result + "행 삭제");
		return "redirect:/";
	}
	
	@PostMapping("/check")
	public ResponseEntity<Integer> memberCheck(@RequestBody Map<String, String> res, HttpServletRequest req) {
		log.info("memberCheck()");
		int result = 0;
		MemberVO memberVO = new MemberVO();
		memberVO = memberService.memberCheck(res.get("memberId"));
		if(memberVO != null && res.get("memberPassword").equals(memberVO.getMemberPassword())) {
			if(memberVO.getManagerId() != 0) {
				log.info(memberVO.getMemberId());
				HttpSession session = req.getSession();
				session.setAttribute("memberId", memberVO.getMemberId());
				session.setAttribute("managerId", memberVO.getManagerId());
//				session.setAttribute("memberName", memberVO.getMemberName());
			} else {
				HttpSession session = req.getSession();
				log.info(memberVO.getMemberId());
				session.setAttribute("memberId", memberVO.getMemberId());
				log.info(session.getAttribute("memberId"));
//				session.setAttribute("memberName", memberVO.getMemberName());
			}
			result = 1;
		} else {
			result = 0;
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/checkout")
	public String memberCheckout(HttpServletRequest req) {
		log.info("memberCheckout()");
		HttpSession session = req.getSession();
		session.removeAttribute("memberId");

		return "redirect:/";
	}
	
	
}






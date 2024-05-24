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
		log.info(result + "�� ���");
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	@PostMapping("/modify")
	public ResponseEntity<Integer> memberUpdate(@RequestBody Map<String, String> res) {
		log.info("memberUpdate()");
		int result = 0;
		MemberVO compareVO = memberService.getMemberById(res.get("memberId"));
		if(compareVO != null) {
			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(res.get("memberId"));
			memberVO.setMemberPassword(res.get("memberPassword"));
			memberVO.setMemberEmail(res.get("memberEmail"));
			if(memberVO.getMemberPassword() == null) {
				// ��й�ȣ�� ������ߴٸ�
				memberVO.setMemberPassword(compareVO.getMemberPassword());
			}
			if(memberVO.getMemberEmail() == null) {
				// �̸����� ������ߴٸ�
				memberVO.setMemberEmail(compareVO.getMemberEmail());
			}
			log.info("memberVO = " + memberVO.toString());
			result = memberService.updateMember(memberVO);
			log.info(result + "�� ����");
		} else {
			result = 0;
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/delete")
	public ResponseEntity<Integer> deletePost(@RequestParam("memberId") String memberId) {
		log.info("delete()");
		// ȸ�� Ż�� ������ �ٷ� ���� �ƴϸ� �÷��� ���� Ż���ߴٰ� ������Ʈ�Ŀ� �����췯�� ���ؼ� �� �÷��� ���� ������ ����� ���� ����غ���.
		int result = memberService.deleteMember(memberId);
		log.info(result + "�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
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
		} else { // ���̵� �Ǵ� ��й�ȣ�� Ʋ�������
			result = 0;
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
}






package com.soldesk.ex01.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/member")
@Log4j
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// TODO 시큐리티적용시 check 옮길것
	
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
	
//	@GetMapping("/detail/{memberId}")
//	public ResponseEntity<MemberVO> detailGet(@PathVariable("memberId") String memberId) {
//		log.info("detailGet()");
//		MemberVO memberVO = memberService.getMemberById(memberId);
////		log.info(memberVO);
//		return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
//	}
	
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
				// 비밀번호를 변경안했다면
				memberVO.setMemberPassword(compareVO.getMemberPassword());
			}
			if(memberVO.getMemberEmail() == null) {
				// 이메일을 변경안했다면
				memberVO.setMemberEmail(compareVO.getMemberEmail());
			}
			log.info("memberVO = " + memberVO.toString());
			result = memberService.updateMember(memberVO);
			log.info(result + "행 수정");
		} else {
			result = 0;
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> deletePost(@RequestBody Map<String, String> res) {
		log.info("delete()");
		// 회원 탈퇴도 삭제를 바로 할지 아니면 컬럼을 만들어서 탈퇴했다고 업데이트후에 스케쥴러로 비교해서 이 컬럼에 값이 있으면 지우게 할지 고민해볼것.
		int result = memberService.deleteMember(res.get("memberId"));	
		log.info(res.get("memberId"));
		log.info(result + "행 삭제");
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
		} else { // 아이디 또는 비밀번호가 틀렸을경우
			result = 0;
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/deleteProperty")
	public ResponseEntity<Integer> removeProperty(
			@RequestParam("memberId") String memberId,
			@RequestParam("propertyIndex") Integer[] propertyIndexList
			) {
		// 리스트로 받은 목록을 넘겨준다.
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(memberId);
		memberVO.setMemberProperty(propertyIndexList);
		int result = memberService.updateMemberProperty(memberVO);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getAllMember")
	public ResponseEntity<List<MemberVO>> getAllMember(){
		log.info("getAllmember()");
		List<MemberVO> memberList = memberService.getAllMember();
		return new ResponseEntity<List<MemberVO>>(memberList, HttpStatus.OK);
	}
	
	@PostMapping("/modifyPw")
	public ResponseEntity<Integer> modifyPw(@RequestBody Map<String, String> res){
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(res.get("memberId"));
//		log.info(memberVO.getManagerId());
		memberVO.setMemberPassword(res.get("memberPassword"));
//		log.info(memberVO.getMemberPassword());
		int result = memberService.updatePassword(memberVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/modifyEmail")
	public ResponseEntity<Integer> modifyEmail(@RequestBody Map<String, String> res){
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(res.get("memberId"));
//		log.info(memberVO.getManagerId());
		memberVO.setMemberEmail(res.get("memberEmail"));
//		log.info(memberVO.getMemberPassword());
		int result = memberService.updateEmail(memberVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	
	
}






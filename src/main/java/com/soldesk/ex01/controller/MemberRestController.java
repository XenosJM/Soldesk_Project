package com.soldesk.ex01.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
// @CrossOrigin(origins = "http://localhost:3000")
@Log4j
public class MemberRestController {
	
	@Autowired
	private MemberService member;
	
	// TODO ��ť��Ƽ����� check �ű��
	
	@PostMapping("/regist")
	public ResponseEntity<Integer> joinMember(@RequestBody Map<String, String> map) {
		log.info("registerPOST()");
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(map.get("memberId"));
		memberVO.setMemberPassword(map.get("memberPassword"));
		memberVO.setMemberEmail(map.get("memberEmail"));
		log.info("memberVO = " + memberVO.toString());
		int result = member.createMember(memberVO);
		log.info(result + "�� ���");
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
	@GetMapping("/detail/{memberId}")
	public ResponseEntity<MemberVO> detailGet(@PathVariable("memberId") String memberId) {
		log.info("detailGet()");
		MemberVO memberVO = member.getMemberById(memberId);
//		log.info(memberVO);
		return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
	}
//	@PostMapping("/modify")
//	public ResponseEntity<Integer> memberUpdate(@RequestBody Map<String, String> res) {
//		log.info("memberUpdate()");
//		int result = 0;
//		MemberVO compareVO = member.getMemberById(res.get("memberId"));
//		if(compareVO != null) {
//			MemberVO memberVO = new MemberVO();
//			memberVO.setMemberId(res.get("memberId"));
//			memberVO.setMemberPassword(res.get("memberPassword"));
//			memberVO.setMemberEmail(res.get("memberEmail"));
//			if(memberVO.getMemberPassword() == null) {
//				// ��й�ȣ�� ������ߴٸ�
//				memberVO.setMemberPassword(compareVO.getMemberPassword());
//			}
//			if(memberVO.getMemberEmail() == null) {
//				// �̸����� ������ߴٸ�
//				memberVO.setMemberEmail(compareVO.getMemberEmail());
//			}
//			log.info("memberVO = " + memberVO.toString());
//			result = member.updateMember(memberVO);
//			log.info(result + "�� ����");
//		} else {
//			result = 0;
//		}
//		return new ResponseEntity<Integer>(result, HttpStatus.OK);
//	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> deletePost(@RequestBody Map<String, String> map) {
		log.info("delete()");
		// ȸ�� Ż�� ������ �ٷ� ���� �ƴϸ� �÷��� ���� Ż���ߴٰ� ������Ʈ�Ŀ� �����췯�� ���ؼ� �� �÷��� ���� ������ ����� ���� ����غ���.
		int result = member.deleteMember(map.get("memberId"));	
		log.info(map.get("memberId"));
		log.info(result + "�� ����");
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/deleteProperty")
	public ResponseEntity<Integer> removeProperty(@RequestBody MemberVO delVO) {
		// ����Ʈ�� ���� ����� �Ѱ��ش�.
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(delVO.getMemberId());
		log.info(memberVO.getMemberId());
		memberVO.setMemberProperty(delVO.getMemberProperty());
		log.info(memberVO.getMemberProperty());
		int result = member.updateMemberProperty(memberVO);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getAllMember")
	public ResponseEntity<List<MemberVO>> getAllMember(){
		log.info("getAllmember()");
		List<MemberVO> memberList = member.getAllMember();
		return new ResponseEntity<List<MemberVO>>(memberList, HttpStatus.OK);
	}
	
	@PostMapping("/modifyPw")
	public ResponseEntity<Integer> modifyPw(@RequestBody Map<String, String> map){
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(map.get("memberId"));
//		log.info(memberVO.getManagerId());
		memberVO.setMemberPassword(map.get("memberPassword"));
//		log.info(memberVO.getMemberPassword());
		int result = member.updatePassword(memberVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/modifyEmail")
	public ResponseEntity<Integer> modifyEmail(@RequestBody Map<String, String> map){
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(map.get("memberId"));
//		log.info(memberVO.getManagerId());
		memberVO.setMemberEmail(map.get("memberEmail"));
//		log.info(memberVO.getMemberPassword());
		int result = member.updateEmail(memberVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/check")
	public ResponseEntity<Integer> memberCheck(@RequestBody Map<String, String> map){
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(map.get("memberId"));
		memberVO.setMemberPassword(map.get("memberPassword"));
		int result = member.checkPassword(memberVO);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/updateRole")
	public ResponseEntity<Integer> updateRole(@RequestBody MemberVO memberVO){
		int result = member.memberRoleUpdate(memberVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	
	
}






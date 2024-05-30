package com.soldesk.ex01.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.MemberService;
import com.soldesk.ex01.service.UtilService;
import com.soldesk.ex01.util.AuthCodeGenerator;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/util")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j
public class UtilRESTController {
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private AuthCodeGenerator authCodeGenerator;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/checkId/{memberId}")
	public ResponseEntity<Integer> checkId(@PathVariable("memberId")String memberId){
		log.info("checkId()");
		log.info(memberId);
//		MemberVO memberVO = utilService.checkId(memberId);
//		log.info(memberVO);
		int result = (utilService.checkId(memberId) != null) ? 1 : 0;
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
		
	}
	
	@GetMapping("/checkEmail")
	public ResponseEntity<Integer> checkEmail(@RequestParam("memberEmail")String memberEmail) {
		log.info("checkEmail()");
//		log.info("memberEmailId : " + memberEmailId);
//		log.info("domain : " + domain);
//		String memberEmail = memberEmailId + domain; 
		log.info("memberEmail : " + memberEmail);
//		log.info("domain : " + domain);
//		String decodeDomain = domain.replace("=", "");
//		log.info("decodeDomain : " + decodeDomain);
//		MemberVO memberVO = utilService.checkEmail(memberEmail);
//		log.info(memberVO);
		int result = (utilService.checkEmail(memberEmail) != null) ? 1 : 0;
		log.info(result);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/authCodeSend")
	public ResponseEntity<Map<String, Integer>> AuthCodeSend(@RequestParam("memberEmail")String memberEmail){
		int result = 0;
		String authCode = null;
		authCode = authCodeGenerator.generateAuthCode();
		String setFrom = "wjdalsqaa123@gmail.com"; // config�� �Է��� �ڽ��� �̸��� �ּҸ� �Է�
        String toMail = memberEmail;
        String title = "�ȳ��ϼ���. Gain �Դϴ�."; // �̸��� ����
        String content =
				"�̸��� ������ȣ�� �߼��Ͽ����ϴ�." + 	
		        "<br><br>" +
		        "���� ��ȣ�� " + authCode + " �Դϴ�." +
		        "<br>" +
		        "������ȣ�� ����� �Է����ּ���"; //�̸��� ���� ����
        // mailSender ��ü�� ���õ� ���� �̿��� ���� ��ü ���� 
		MimeMessage mail = mailSender.createMimeMessage();
		Map<String, Integer> res = new HashMap<>();
		try {
			// �̸��� ���õ� ����(���� ������)�� ������ helper ��ü ����.
            // true�� �����Ͽ� multipart ������ �޽����� �����ϰ�, "utf-8"�� �����Ͽ� ���� ���ڵ��� ����
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			helper.setFrom(setFrom); // �߽��� ����
			helper.setTo(toMail); // ������ ����
			helper.setSubject(title); // ���� ����
			helper.setText(content, true); // ���� ����, html ����
			// TODO ���� ���ε� �߰��� ���� 
			
			mailSender.send(mail); 
			result = Integer.parseInt(authCode); // ���ڿ��� ������� �����ڵ带 ���ڷ� �Ľ��ؼ� ����
			res.put("result", 1);
			res.put("authCode", result);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = 0;
			res.put("result", 0);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/authCodeId")
	public ResponseEntity<Integer> AuthIdSend(@RequestParam("memberEmail")String memberEmail){
		int result = 0;
		MemberVO memberVO = memberService.findId(memberEmail);
		if(memberVO == null) {
			result =0;
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		}
		String memberId = memberVO.getMemberId();
		String sealedId = memberId.substring(0, memberId.length()-3);
		String setFrom = "wjdalsqaa123@gmail.com"; // config�� �Է��� �ڽ��� �̸��� �ּҸ� �Է�
        String toMail = memberEmail;
        String title = "�ȳ��ϼ���. Gain �Դϴ�."; // �̸��� ����
        String content =
				"��û�Ͻ� ȸ������ ���̵��Դϴ�. ���Ȼ� ���� 3�ڸ��� ������ �˷��帳�ϴ�." + 	
		        "<br><br>" +
		        "ȸ������ ���̵�� " + sealedId + "***" + " �Դϴ�." +
		        "<br>" +
		        "�̿��� �ּż� �����մϴ�."; //�̸��� ���� ����
        // mailSender ��ü�� ���õ� ���� �̿��� ���� ��ü ���� 
		MimeMessage mail = mailSender.createMimeMessage();
			try {
				// �̸��� ���õ� ����(���� ������)�� ������ helper ��ü ����.
	            // true�� �����Ͽ� multipart ������ �޽����� �����ϰ�, "utf-8"�� �����Ͽ� ���� ���ڵ��� ����
				MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
				helper.setFrom(setFrom); // �߽��� ����
				helper.setTo(toMail); // ������ ����
				helper.setSubject(title); // ���� ����
				helper.setText(content, true); // ���� ����, html ����
				// TODO ���� ���ε� �߰��� ���� 
				
				mailSender.send(mail); 
				result = 1; 
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = 0;
			}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/modifyPw")
	public ResponseEntity<Integer> updatePw(@RequestBody Map<String, String> res ){
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(res.get("memberId"));
//		log.info(memberVO.getManagerId());
		memberVO.setMemberEmail(res.get("memberEmail"));
//		log.info(memberVO.getMemberEmail());
		memberVO.setMemberPassword(res.get("memberPassword"));
//		log.info(memberVO.getMemberPassword());
		int result = memberService.updateMember(memberVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/findId")
	public ResponseEntity<String> checkIdEmail(@RequestParam("memberEmail") String memberEmail){
		MemberVO memberVO = memberService.findId(memberEmail);
		log.info(memberVO);
		String result = (memberVO != null) ? memberVO.getMemberId() : null;
		log.info(result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
}






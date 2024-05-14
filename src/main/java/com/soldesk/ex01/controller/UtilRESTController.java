package com.soldesk.ex01.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.UtilService;
import com.soldesk.ex01.util.AuthCodeGenerator;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/util")
@Log4j
public class UtilRESTController {
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private AuthCodeGenerator authCodeGenerator;
	
	@GetMapping("/checkId/{memberId}")
	public ResponseEntity<Integer> checkId(@PathVariable("memberId")String memberId){
		log.info("checkId()");
		log.info(memberId);
		MemberVO memberVO = utilService.checkId(memberId);
		log.info(memberVO);
		int result = (memberVO != null) ? 1 : 0;
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
		MemberVO memberVO = utilService.checkEmail(memberEmail);
//		log.info(memberVO);
		int result = (memberVO != null) ? 1 : 0;
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/authCodeSend")
	public ResponseEntity<Integer> AuthCodeSend(@RequestParam("memberEmail")String memberEmail){
		int result = 0;
		
		String authCode = authCodeGenerator.generateAuthCode();
		String setFrom = "wjdalsqaa123@gmail.com"; // email-config�� ������ �ڽ��� �̸��� �ּҸ� �Է�
        String toMail = memberEmail;
        String title = "ȸ�� ���� ���� �̸��� �Դϴ�."; // �̸��� ����
        String content =
                "���� ����Ʈ�� ȸ�������� ȯ���մϴ�." + 	//html �������� �ۼ� !
                        "<br><br>" +
                        "���� ��ȣ�� " + authCode + "�Դϴ�." +
                        "<br>" +
                        "������ȣ�� ����� �Է����ּ���"; //�̸��� ���� ����
		MimeMessage mail = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			// TODO ���� ���ε� �߰��� ���� ����
			
			mailSender.send(mail);
			result = Integer.parseInt(authCode);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = 0;
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}

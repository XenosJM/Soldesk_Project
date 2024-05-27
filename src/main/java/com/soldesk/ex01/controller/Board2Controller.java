package com.soldesk.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.ReplyVO;
import com.soldesk.ex01.service.AttachService;
import com.soldesk.ex01.service.Board2Service;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.service.ReplyService;
import com.soldesk.ex01.service.RereplyService;
import com.soldesk.ex01.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/board2")
@Log4j
public class Board2Controller {
	
	@Autowired
	private Board2Service board2Service;
	
	@Autowired
	private String uploadPath;

	@Autowired
	private AttachService attachService;
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private RereplyService rereplyService;


	@PostMapping("/regist")
	public String registerPost(Board2VO vo, RedirectAttributes reAttr) {
		log.info("board controller : registerPost()");
		log.info("board controller : Board2VO =" + vo);
		
		MultipartFile file = vo.getFile();

		int result = board2Service.insertBoard(vo);
		log.info(result + "�� ����");
		
		
		
		return "redirect:/";
	}



	@PostMapping("/update")
	public String updatePost(Board2VO vo, RedirectAttributes reAttr) {
		log.info("board controller : updatePost()");
		
		int result = board2Service.updateBoard(vo);
		log.info(result + "�� ����");
		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String delete(Integer boardId, RedirectAttributes reAttr) {
		int result;
		log.info("board controller : deletePost()");
		Board2VO vo = board2Service.selectDetail(boardId);
		
		List<ReplyVO> list = replyService.selectReplyBoard(boardId);
		for(int i = 0;i<list.size();i++) {
			result = rereplyService.deleteRereplyToReply(list.get(i).getReplyId());
			log.info("����"+result + "�� ����");
			result = replyService.deleteReply(list.get(i).getReplyId());
			log.info("���"+result + "�� ����");
		}
		
		result = board2Service.deleteBoard(boardId);
		log.info("�Խñ�" +result + "�� ����");
		return "redirect:/board/list";
	}

	
	
	// ÷�� ���� ���ε� ������ �̵�(GET)
	@GetMapping("/registAttach")
	public void registerGET() {
		log.info("registerGET()");
	} // end registerGET()

	// ÷�� ���� ���ε� ó��(POST)
	@PostMapping("/attach")
	public String attachPOST(AttachVO attachVO) {
		log.info("attachPost()");
		log.info("attachVO = " + attachVO);
		MultipartFile file = attachVO.getFile();

		String chgName = UUID.randomUUID().toString();

		FileUploadUtil.saveFile(uploadPath, file, chgName);

		attachVO.setAttachPath(FileUploadUtil.makeDatePath());

		attachVO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));

		attachVO.setAttachChgName(chgName);

		attachVO.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));

		log.info(attachService.createAttach(attachVO) + "�� ���");
		

		return "redirect:/board/list";
	} // end attachPOST()

	// ÷�� ���� �ٿ�ε�(GET)
	// ������ Ŭ���ϸ� ����ڰ� �ٿ�ε��ϴ� ���
	// ���� ���ҽ��� �񵿱�� �����Ͽ� ���� �ٿ�ε�
//	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	@ResponseBody
//	public ResponseEntity<Resource> download(int boardId) throws IOException {
//		log.info("download()");
//
//		// attachId�� �� ���� ��ȸ
////		AttachVO attachVO = attachService.getAttachById(attachId);
//		Board2VO boardVO = board2Service.selectDetail(boardId);
//		String attachPath = boardVO.getAttachPath();
//		String attachChgName = boardVO.getAttachChgName();
//		String attachExtension = boardVO.getAttachExtension();
//		String attachRealName = boardVO.getAttachRealName();
//
//		// ������ ����� ���� ���� ����
//		String resourcePath = uploadPath + File.separator + attachPath + File.separator + attachChgName;
//		// ���� ���ҽ� ����
//		Resource resource = new FileSystemResource(resourcePath);
//		// �ٿ�ε��� ���� �̸��� ����� ����
//		HttpHeaders headers = new HttpHeaders();
//		String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
//		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachName + "." + attachExtension);
//
//		// ������ Ŭ���̾�Ʈ�� ����
//		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
//	} // end download()

}

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.service.AttachService;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/board")
@Log4j
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public void list(Model model) {
		log.info("board controller : list()");
		List<BoardVO> boardList = boardService.selectList();

		model.addAttribute("boardList", boardList);
	}

	@GetMapping("/regist")
	public void registerGet() {
		log.info("board controller : registerGet()");
	}

	@PostMapping("/regist")
	public String registerPost(BoardVO vo, RedirectAttributes reAttr) {
		log.info("board controller : registerPost()");
		log.info("board controller : BoardVO =" + vo);
		int result = boardService.insertBoard(vo);
		log.info(result + "�� ����");
		return "redirect:/";
	}

	@GetMapping("/detail")
	public void detail(Model model, Integer boardId) {
		log.info("board controller : detail()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		model.addAttribute("boardVO", boardVO);
	}

	@GetMapping("/update")
	public void updateGet(Model model, Integer boardId) {
		log.info("board controller : updateGet()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		model.addAttribute("boardVO", boardVO);
	}

	@PostMapping("/update")
	public String updatePost(BoardVO vo, RedirectAttributes reAttr) {
		log.info("board controller : updatePost()");
		int result = boardService.updateBoard(vo);
		log.info(result + "�� ����");
		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String delete(Integer boardId, RedirectAttributes reAttr) {
		log.info("board controller : deletePost()");
		int result = boardService.deleteBoard(boardId);
		log.info(result + "�� ����");
		return "redirect:/board/list";
	}

	@Autowired
	private String uploadPath;

	@Autowired
	private AttachService attachService;

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
		

		return "redirect:/board/regist";
	} // end attachPOST()

	// ÷�� ���� ��� ��ȸ(GET)
	@GetMapping("/listAttach")
	public void listAttach(Model model) {
		// ÷�� ���� attachId ����Ʈ�� Model�� �߰��Ͽ� ����
		model.addAttribute("idList", attachService.getAllId());
		log.info("list()");
	}

	// ÷�� ���� �� ���� ��ȸ(GET)
	@GetMapping("/detailAttach")
	public void detail(int attachId, Model model) {
		log.info("detail()");
		log.info("attachId : " + attachId);
		// attachId�� �� ���� ��ȸ
		AttachVO attachVO = attachService.getAttachById(attachId);
		// ��ȸ�� �� ������ Model�� �߰��Ͽ� ����
		model.addAttribute("attachVO", attachVO);
	} // end detail()

	// ÷�� ���� �ٿ�ε�(GET)
	// ������ Ŭ���ϸ� ����ڰ� �ٿ�ε��ϴ� ���
	// ���� ���ҽ��� �񵿱�� �����Ͽ� ���� �ٿ�ε�
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(int attachId) throws IOException {
		log.info("download()");

		// attachId�� �� ���� ��ȸ
		AttachVO attachVO = attachService.getAttachById(attachId);
		String attachPath = attachVO.getAttachPath();
		String attachChgName = attachVO.getAttachChgName();
		String attachExtension = attachVO.getAttachExtension();
		String attachRealName = attachVO.getAttachRealName();

		// ������ ����� ���� ���� ����
		String resourcePath = uploadPath + File.separator + attachPath + File.separator + attachChgName;
		// ���� ���ҽ� ����
		Resource resource = new FileSystemResource(resourcePath);
		// �ٿ�ε��� ���� �̸��� ����� ����
		HttpHeaders headers = new HttpHeaders();
		String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachName + "." + attachExtension);

		// ������ Ŭ���̾�Ʈ�� ����
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // end download()

}

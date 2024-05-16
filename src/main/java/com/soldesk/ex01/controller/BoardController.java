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
		log.info(result + "행 삽입");
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
		log.info(result + "행 수정");
		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String delete(Integer boardId, RedirectAttributes reAttr) {
		log.info("board controller : deletePost()");
		int result = boardService.deleteBoard(boardId);
		log.info(result + "행 삭제");
		return "redirect:/board/list";
	}

	@Autowired
	private String uploadPath;

	@Autowired
	private AttachService attachService;

	// 첨부 파일 업로드 페이지 이동(GET)
	@GetMapping("/registAttach")
	public void registerGET() {
		log.info("registerGET()");
	} // end registerGET()

	// 첨부 파일 업로드 처리(POST)
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

		log.info(attachService.createAttach(attachVO) + "행 등록");
		

		return "redirect:/board/regist";
	} // end attachPOST()

	// 첨부 파일 목록 조회(GET)
	@GetMapping("/listAttach")
	public void listAttach(Model model) {
		// 첨부 파일 attachId 리스트를 Model에 추가하여 전달
		model.addAttribute("idList", attachService.getAllId());
		log.info("list()");
	}

	// 첨부 파일 상세 정보 조회(GET)
	@GetMapping("/detailAttach")
	public void detail(int attachId, Model model) {
		log.info("detail()");
		log.info("attachId : " + attachId);
		// attachId로 상세 정보 조회
		AttachVO attachVO = attachService.getAttachById(attachId);
		// 조회된 상세 정보를 Model에 추가하여 전달
		model.addAttribute("attachVO", attachVO);
	} // end detail()

	// 첨부 파일 다운로드(GET)
	// 파일을 클릭하면 사용자가 다운로드하는 방식
	// 파일 리소스를 비동기로 전송하여 파일 다운로드
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(int attachId) throws IOException {
		log.info("download()");

		// attachId로 상세 정보 조회
		AttachVO attachVO = attachService.getAttachById(attachId);
		String attachPath = attachVO.getAttachPath();
		String attachChgName = attachVO.getAttachChgName();
		String attachExtension = attachVO.getAttachExtension();
		String attachRealName = attachVO.getAttachRealName();

		// 서버에 저장된 파일 정보 생성
		String resourcePath = uploadPath + File.separator + attachPath + File.separator + attachChgName;
		// 파일 리소스 생성
		Resource resource = new FileSystemResource(resourcePath);
		// 다운로드할 파일 이름을 헤더에 설정
		HttpHeaders headers = new HttpHeaders();
		String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachName + "." + attachExtension);

		// 파일을 클라이언트로 전송
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // end download()

}

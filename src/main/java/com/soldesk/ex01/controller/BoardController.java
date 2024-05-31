package com.soldesk.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping(value = "/board")
@Log4j
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private String uploadPath;

	@Autowired
	private AttachService attachService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private RereplyService rereplyService;

	@Autowired
	private Board2Service board2Service;

	@GetMapping("/test")
	public void testGET() {
		log.info("testGET()");
	}

//	@PostMapping("/registest")
//	public String registerPost(Board2VO vo, AttachVO attachvo,RedirectAttributes reAttr) {
//		log.info("board controller : registerPost()");
//		log.info("board controller : Board2VO =" + vo);
//
//		MultipartFile file = attachvo.getFile();
//		String chgName = UUID.randomUUID().toString();
//		attachvo.setAttachPath(FileUploadUtil.makeDatePath());
//		attachvo.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
//		attachvo.setAttachChgName(chgName);
//		attachvo.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));
//
//		int result = board2Service.insertBoard(vo);
//		log.info(result + "행 삽입(보드)");
//		result = attachService.createAttach(attachvo);
//		log.info(result+"행 삽입(어태치)");
//
//		return "redirect:/";
//	}

//	@PostMapping("/regist")
//	public String registerPost(BoardVO vo, RedirectAttributes reAttr) {
//		log.info("board controller : registerPost()");
//		log.info("board controller : BoardVO =" + vo);
//
//		MultipartFile file = vo.getFile();
//
//		if (file.isEmpty()) {
//			vo.setAttachPath("");
//			vo.setAttachRealName("");
//			vo.setAttachChgName("");
//			vo.setAttachExtension("");
//		} else {
//			String chgName = UUID.randomUUID().toString();
//			FileUploadUtil.saveFile(uploadPath, file, chgName);
//			vo.setAttachPath(FileUploadUtil.makeDatePath());
//			vo.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
//			vo.setAttachChgName(chgName);
//			vo.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));
//		}
//		int result = boardService.insertBoard(vo);
//		log.info(result + "행 삽입");
//
//		return "redirect:/";
//	}

	@PostMapping("/regist")
	public String registerPost(Board2VO vo, RedirectAttributes reAttr) {
		log.info("board controller : registerPost()");
		log.info("board controller : Board2VO =" + vo);
		int result = board2Service.insertBoard(vo);
		log.info("보드 " + result + "행 삽입");
		AttachVO[] attach = vo.getAttachVO();
		if (attach != null) {
			for (int i = 0; i < attach.length; i++) {				
				log.info("첨부 파일 경로: " + attach[i].getAttachPath());
				log.info("첨부 파일 실제 이름: " + attach[i].getAttachRealName());
				log.info("첨부 파일 변경된 이름: " + attach[i].getAttachChgName());
				log.info("첨부 파일 확장자: " + attach[i].getAttachExtension());
			}
		} else {
			log.info("첨부 파일이 없습니다.");
		}
		return "redirect:/board/list";
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
		int result;
		log.info("board controller : deletePost()");
		BoardVO vo = boardService.selectDetail(boardId);

		List<ReplyVO> list = replyService.selectReplyBoard(boardId);
		for (int i = 0; i < list.size(); i++) {
			result = rereplyService.deleteRereplyToReply(list.get(i).getReplyId());
			log.info("대댓글" + result + "행 삭제");
			result = replyService.deleteReply(list.get(i).getReplyId());
			log.info("댓글" + result + "행 삭제");
		}

		File file = new File("C:\\upload\\ex01\\2024\\05\\24\\" + vo.getAttachChgName());
		if (file.exists()) {
			log.info("파일 삭제 결과 : " + file.delete());
		}
		result = boardService.deleteBoard(boardId);
		log.info("게시글" + result + "행 삭제");
		return "redirect:/board/list";
	}

	// 첨부 파일 업로드 페이지 이동(GET)
	@GetMapping("/registAttach")
	public void registerGET() {
		log.info("registerGET()");
	} // end registerGET()

	// 첨부 파일 업로드 처리(POST)
	@PostMapping("/attach")
	@ResponseBody
	public ResponseEntity<Map<String, String>> attachPOST(AttachVO attachVO) {
		log.info("attachPost()");
		log.info("attachVO = " + attachVO);
		MultipartFile file = attachVO.getFile();

		String chgName = UUID.randomUUID().toString();

		FileUploadUtil.saveFile(uploadPath, file, chgName);
		attachVO.setAttachPath(FileUploadUtil.makeDatePath());
		attachVO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
		attachVO.setAttachChgName(chgName);
		attachVO.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));

//		log.info(attachService.createAttach(attachVO) + "행 등록");

		Map<String, String> response = new HashMap<>();
		response.put("attachPath", attachVO.getAttachPath());
		response.put("attachRealName", attachVO.getAttachRealName());
		response.put("attachChgName", attachVO.getAttachChgName());
		response.put("attachExtension", attachVO.getAttachExtension());
		return new ResponseEntity<>(response, HttpStatus.OK);
	} // end attachPOST()

	// 첨부 파일 다운로드(GET)
	// 파일을 클릭하면 사용자가 다운로드하는 방식
	// 파일 리소스를 비동기로 전송하여 파일 다운로드
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(int boardId) throws IOException {
		log.info("download()");

		// attachId로 상세 정보 조회
//		AttachVO attachVO = attachService.getAttachById(attachId);
		BoardVO boardVO = boardService.selectDetail(boardId);
		String attachPath = boardVO.getAttachPath();
		String attachChgName = boardVO.getAttachChgName();
		String attachExtension = boardVO.getAttachExtension();
		String attachRealName = boardVO.getAttachRealName();

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

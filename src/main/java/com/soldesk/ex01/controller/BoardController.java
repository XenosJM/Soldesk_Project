package com.soldesk.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private String uploadPath;

	@Autowired
	private AttachService attachService;



	@Autowired
	private Board2Service board2Service;

	@GetMapping("/test")
	public void testGET() {
		log.info("testGET()");
	}

	@PostMapping("/regist")
	public String registerPost(Board2VO vo, RedirectAttributes reAttr) {
		log.info("board controller : registerPost()");
		log.info("board controller : Board2VO =" + vo);
		int result = board2Service.insertBoard(vo);
		log.info("보드 " + result + "행 삽입");
//		AttachVO[] attach = vo.getAttachVO();
//		if (attach != null) {
//			for (int i = 0; i < attach.length; i++) {				
//				log.info("첨부 파일 경로: " + attach[i].getAttachPath());
//				log.info("첨부 파일 실제 이름: " + attach[i].getAttachRealName());
//				log.info("첨부 파일 변경된 이름: " + attach[i].getAttachChgName());
//				log.info("첨부 파일 확장자: " + attach[i].getAttachExtension());
//			}
//		} else {
//			log.info("첨부 파일이 없습니다.");
//		}
		return "redirect:/board/list";
	}

	
	
	@PostMapping("/update")
	public String updatePost(Board2VO vo, RedirectAttributes reAttr) {
		log.info("board controller : updatePost()");
		log.info(vo);
		int result = board2Service.updateBoard(vo);
		AttachVO[] attach = vo.getAttachVO();
		if(attach!=null) {
			for(int i = 0; i<attach.length;i++) {
				log.info("첨부 파일 경로: " + attach[i].getAttachPath());
				log.info("첨부 파일 실제 이름: " + attach[i].getAttachRealName());
				log.info("첨부 파일 변경된 이름: " + attach[i].getAttachChgName());
				log.info("첨부 파일 확장자: " + attach[i].getAttachExtension());
			}
		}
		log.info(result + "행 수정");
		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String delete(Integer boardId, RedirectAttributes reAttr) {
		log.info("board controller : deletePost()");
		int result = board2Service.deleteBoard(boardId);
		log.info("게시글" + result + "행 삭제");
		return "redirect:/board/list";
	}

	// 첨부 파일 업로드 페이지 이동(GET)
	@GetMapping("/registAttach")
	public void registerGET() {
		log.info("registerGET()");
	} // end registerGET()

	
	@PostMapping("/attach")
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> attachPOST(@RequestParam("file") List<MultipartFile> files) {
	    log.info("attachPost()");
	    
	    List<Map<String, String>> responseList = new ArrayList<>();

	    for (MultipartFile file : files) {
	        String chgName = UUID.randomUUID().toString();

	        FileUploadUtil.saveFile(uploadPath, file, chgName);

	        AttachVO attachVO = new AttachVO();
	        attachVO.setAttachPath(FileUploadUtil.makeDatePath());
	        attachVO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
	        attachVO.setAttachChgName(chgName);
	        attachVO.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));

	        Map<String, String> response = new HashMap<>();
	        response.put("attachPath", attachVO.getAttachPath());
	        response.put("attachRealName", attachVO.getAttachRealName());
	        response.put("attachChgName", attachVO.getAttachChgName());
	        response.put("attachExtension", attachVO.getAttachExtension());

	        responseList.add(response);
	    }

	    return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	// 첨부 파일 다운로드(GET)
	// 파일을 클릭하면 사용자가 다운로드하는 방식
	// 파일 리소스를 비동기로 전송하여 파일 다운로드
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(int attachId) throws IOException {
		log.info("download()");

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

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

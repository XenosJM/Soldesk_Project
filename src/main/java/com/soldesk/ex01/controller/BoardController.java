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
import org.springframework.web.bind.annotation.RequestBody;
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
		
		
		int result = board2Service.insertBoard(vo);
		
//		AttachVO[] attach = vo.getAttachVO();
//		if (attach != null) {
//			for (int i = 0; i < attach.length; i++) {				
//				log.info("÷�� ���� ���: " + attach[i].getAttachPath());
//				log.info("÷�� ���� ���� �̸�: " + attach[i].getAttachRealName());
//				log.info("÷�� ���� ����� �̸�: " + attach[i].getAttachChgName());
//				log.info("÷�� ���� Ȯ����: " + attach[i].getAttachExtension());
//			}
//		} else {
//			log.info("÷�� ������ �����ϴ�.");
//		}
		return "redirect:/board/list";
	}
	
//	@PostMapping("/regist")
//	public ResponseEntity<Integer> registerPost(@RequestBody Board2VO vo) {
//		log.info("board controller : registerPost()");
//		log.info("board controller : Board2VO =" + vo);
//		int result = board2Service.insertBoard(vo);
//		log.info("���� " + result + "�� ����");
////		AttachVO[] attach = vo.getAttachVO();
////		if (attach != null) {
////			for (int i = 0; i < attach.length; i++) {				
////				log.info("÷�� ���� ���: " + attach[i].getAttachPath());
////				log.info("÷�� ���� ���� �̸�: " + attach[i].getAttachRealName());
////				log.info("÷�� ���� ����� �̸�: " + attach[i].getAttachChgName());
////				log.info("÷�� ���� Ȯ����: " + attach[i].getAttachExtension());
////			}
////		} else {
////			log.info("÷�� ������ �����ϴ�.");
////		}
//		return new ResponseEntity<Integer>(result,HttpStatus.OK);
//	}
//	
	@PostMapping("/recommend")
	public ResponseEntity<Integer> recommend(Integer boardId) {
		log.info("board controller : recommend()");
		int result = board2Service.recommendIncrease(boardId);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
		
	}

	
	
	@PostMapping("/update")
	public String updatePost(Board2VO vo, RedirectAttributes reAttr) {
		log.info("board controller : updatePost()");
		log.info(vo);
		int result = board2Service.updateBoard(vo);
		AttachVO[] attach = vo.getAttachVO();
		if(attach!=null) {
			for(int i = 0; i<attach.length;i++) {
				log.info("÷�� ���� ���: " + attach[i].getAttachPath());
				log.info("÷�� ���� ���� �̸�: " + attach[i].getAttachRealName());
				log.info("÷�� ���� ����� �̸�: " + attach[i].getAttachChgName());
				log.info("÷�� ���� Ȯ����: " + attach[i].getAttachExtension());
			}
		}
		log.info(result + "�� ����");
		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String delete(Integer boardId, RedirectAttributes reAttr) {
		log.info("board controller : deletePost()");
		int result = board2Service.deleteBoard(boardId);
		log.info("�Խñ�" + result + "�� ����");
		return "redirect:/board/list";
	}

	// ÷�� ���� ���ε� ������ �̵�(GET)
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
	// ÷�� ���� �ٿ�ε�(GET)
	// ������ Ŭ���ϸ� ����ڰ� �ٿ�ε��ϴ� ���
	// ���� ���ҽ��� �񵿱�� �����Ͽ� ���� �ٿ�ε�
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(int attachId) throws IOException {
		log.info("download()");

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

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

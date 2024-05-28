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
		log.info(result + "Çà »ðÀÔ");
		
		
		
		
		return "redirect:/";
	}

	

	   
	   @GetMapping("/register")
	   public void registerGET() {
	      log.info("registerGET()");
	   } // end registerGET()

	  
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
	    
	      log.info(attachService.createAttach(attachVO) + "Çà»ðÀÔ");

	      return "redirect:/list";
	   } // end attachPOST()
	   
	    
	    @GetMapping("/list")
	    public void list(Model model) {
	       
	        model.addAttribute("idList", attachService.getAllId());
	        log.info("list()");
	    }

	    
	    @GetMapping("/detail")
	    public void detail(int attachId, Model model) {
	        log.info("detail()");
	        log.info("attachId : " + attachId);
	        
	        AttachVO attachVO = attachService.getAttachById(attachId);
	       
	        model.addAttribute("attachVO", attachVO);
	    } // end detail()
	    
	
	    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	    @ResponseBody
	    public ResponseEntity<Resource> download(int attachId) throws IOException {
	        log.info("download()");
	        
	      
	        AttachVO attachVO = attachService.getAttachById(attachId);
	        String attachPath = attachVO.getAttachPath();
	        String attachChgName = attachVO.getAttachChgName();
	        String attachExtension = attachVO.getAttachExtension();
	        String attachRealName = attachVO.getAttachRealName();
	        
	      
	        String resourcePath = uploadPath + File.separator + attachPath 
	                                    + File.separator + attachChgName;
	       
	        Resource resource = new FileSystemResource(resourcePath);
	       
	        HttpHeaders headers = new HttpHeaders();
	        String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" 
	              + attachName + "." + attachExtension);

	       
	        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	    } // end download()

}

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
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.RecommendVO;
import com.soldesk.ex01.service.AttachService;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.service.RecommendService;
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
	private BoardService boardService;
	
	@Autowired
	private RecommendService recommendService;

	

//	@PostMapping("/regist")
//	public String registerPost(BoardVO vo, RedirectAttributes reAttr) {
//		int result = boardService.insertBoard(vo);
//		return "redirect:/board/list?categoryId="+vo.getCategoryId();
//	}

	@PostMapping("/regist")
	public ResponseEntity<Integer> registerPost(@RequestBody BoardVO vo) {
		log.info("board controller : registerPost()");
		log.info("board controller : Board2VO =" + vo);
		int result = boardService.insertBoard(vo);

		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	

	@PostMapping("/recommend")
	@ResponseBody
	public ResponseEntity<Integer> increaseRecommend(@RequestParam("boardId") int boardId,@RequestParam("memberId")String memberId){
		int result;
		if(recommendService.checkRecommend(boardId, memberId)) {
			result = 0;
		}else {
			RecommendVO vo = recommendService.selectRecommend(boardId);
			if(vo.getRecommendMemberString()==null || vo.getRecommendMemberString().isEmpty()) {
				String[] recommendMember= {memberId};
				vo.setRecommendMember(recommendMember);
				recommendService.updateRecommendMember(vo);
			}else {
				String[] oldRecommendMember = vo.getRecommendMemberString().replaceAll("\\[|\\]", "").split(", ");
				String[] newRecommendMember = new String[oldRecommendMember.length+1];
				for(int i = 0;i<oldRecommendMember.length;i++) {
					newRecommendMember[i]=oldRecommendMember[i];
				}
				newRecommendMember[oldRecommendMember.length] = memberId;
				vo.setRecommendMember(newRecommendMember);
				recommendService.updateRecommendMember(vo);
			}
			result = boardService.increaseRecommend(boardId);
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	
	@PostMapping("/update")
	public String updatePost(BoardVO vo, RedirectAttributes reAttr) {
		log.info("board controller : updatePost()");
		log.info(vo);
		int result = boardService.updateBoard(vo);
		return "redirect:/board/detail?boardId="+vo.getBoardId();
	}
	
//	@PostMapping("/update")
//	public ResponseEntity<Integer> updatePost(@RequestBody BoardVO vo) {
//		log.info("board controller : updatePost()");
//		log.info(vo);
//		int result = boardService.updateBoard(vo);
//		return new ResponseEntity<>(result,HttpStatus.OK);
//	}
	
	

//	@PostMapping("/delete")
//	public String delete(Integer boardId, RedirectAttributes reAttr) {
//		log.info("board controller : deletePost()");
//		int categoryId = boardService.selectDetail(boardId).getCategoryId();
//		int result = boardService.deleteBoard(boardId);
//		return "redirect:/board/list?categoryId="+categoryId;
//	}
//

	@PostMapping("/delete")
	public ResponseEntity<Integer> delete(@RequestBody BoardVO vo){
		int result = boardService.deleteBoard(vo);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	

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

	
	

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

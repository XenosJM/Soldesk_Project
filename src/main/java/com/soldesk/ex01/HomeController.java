package com.soldesk.ex01;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.BoardVO;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.domain.RecommendVO;
import com.soldesk.ex01.service.AttachService;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.service.FriendService;
import com.soldesk.ex01.service.MemberService;
import com.soldesk.ex01.service.RecommendService;
import com.soldesk.ex01.util.PageMaker;
import com.soldesk.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Log4j
public class HomeController {

//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class); // 

	@Autowired
	private MemberService memberService;

	@Autowired
	private String uploadPath;

	@Autowired
	private BoardService boardService;

	@Autowired
	private FriendService friendService;

	@Autowired
	private AttachService attachService;

	@Autowired
	private RecommendService recommendService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );

		return "main";
	}

	@GetMapping(value = "board/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(int attachId) throws IOException {
		log.info("download()");

		AttachVO attachVO = attachService.getAttachById(attachId);
		String attachPath = attachVO.getAttachPath();
		String attachChgName = attachVO.getAttachChgName();
		String attachExtension = attachVO.getAttachExtension();
		String attachRealName = attachVO.getAttachRealName();

		String resourcePath = uploadPath + File.separator + attachPath + File.separator + attachChgName;

		Resource resource = new FileSystemResource(resourcePath);

		HttpHeaders headers = new HttpHeaders();
		String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachName + "." + attachExtension);

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // end download()

	@GetMapping("board/detail")
	public void boardDetail(Model model, Integer boardId) {
		log.info("board controller : detail()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		log.info(boardVO);
		// RecommendVO recommendVO = recommendService.selectRecommend(boardId);

		model.addAttribute("boardVO", boardVO);
		// model.addAttribute("recommendVO",recommendVO);
	}

//	@GetMapping("board/detail")
//	@ResponseBody 
//	public ResponseEntity<BoardVO> boardDetail(@RequestParam Integer boardId) {
//		log.info("board controller : detail()");
//		BoardVO boardVO = boardService.selectDetail(boardId);
//		if (boardVO != null) {
//			return new ResponseEntity<>(boardVO, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping("board/regist")
	public void boardRegister() {
		log.info("board controller : registerGet()");
	}

	@GetMapping("board/list")
	public void list(Model model, Pagination pagination, @RequestParam int categoryId) {
		log.info("list()");
		log.info("pagination = " + pagination);
		List<BoardVO> boardList = boardService.getPagingBoards(pagination);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(boardService.getTotalCount(pagination));

		String test = "[test,test2,test3]";
		String[] strArray = test.replaceAll("\\[|\\]", "").split(", ");
		System.out.println("strarray = " + Arrays.toString(strArray));

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardList", boardList);
	}

//	@GetMapping("board/list")
//	   public ResponseEntity<Map<String, Object>> list(Pagination pagination) {
//	       log.info("list()");
//	       log.info("pagination = " + pagination);
//
//	       List<BoardVO> boardList = boardService.getPagingBoards(pagination);
//
//	       PageMaker pageMaker = new PageMaker();
//	       pageMaker.setPagination(pagination);
//	       pageMaker.setTotalCount(boardService.getTotalCount(pagination));
//	       
//	       Map<String, Object> response = new HashMap<>();
//	       response.put("pageMaker", pageMaker);
//	       response.put("boardList", boardList);
//
//	       
//	       return new ResponseEntity<>(response,HttpStatus.OK);
//	   }

	@GetMapping("board/recommendlist")
	public ResponseEntity<Map<String, Object>> list(Pagination pagination) {
		List<BoardVO> boardList = boardService.selectListByRecommend(pagination);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(boardService.selectTotalCountByRecommend(pagination));
		Map<String, Object> response = new HashMap<>();
		response.put("pageMaker", pageMaker);
		response.put("boardList", boardList);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("board/update")
	public void boardUpdate(Model model, Integer boardId) {
		log.info("board controller : updateGet()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		boardVO.setAttachVO(attachService.getAttachByBoardId(boardId));
		model.addAttribute("board2VO", boardVO);
	}

//	@GetMapping("board/update")
//	@ResponseBody
//	public ResponseEntity<BoardVO> boardUpdate(Integer boardId,@RequestBody Pagination pagination) {
//		log.info("board controller : updateGet()");
//		BoardVO boardVO = boardService.selectDetail(boardId);
//		boardVO.setAttachVO(attachService.getAttachByBoardId(boardId));
//		return new ResponseEntity<>(boardVO,HttpStatus.OK);
//	}



	@GetMapping("member/regist")
	public void joinMember() {
		log.info("joinMember()");
	}

//	@GetMapping("member/detail")
//	public void detailGet(Model model, HttpServletRequest req) {
//		log.info("detailGet()");
//		MemberVO memberVO = memberService.getMemberById(req.getUserPrincipal().getName());
//		log.info(memberVO);
//		model.addAttribute("memberVO", memberVO);
//	}

	@GetMapping("member/update")
	public void updateGet() {
		log.info("updateGet()");
	}

	@GetMapping("member/findIdPw")
	public void findIdPw() {
		log.info("findIdPw()");
	}

//	@GetMapping("member/friendList")
//	public void getFriendList(Model model, HttpServletRequest req) throws JsonProcessingException {
//		HttpSession session = req.getSession();
//		List<FriendVO> friendList = friendService.friendList((String)session.getAttribute("memberId"));
//		ObjectMapper objectMapper = new ObjectMapper();
//	    String friendListJson = objectMapper.writeValueAsString(friendList);
//	    model.addAttribute("friendList", friendListJson);
//	}
	@GetMapping("member/friendList")
	public void getFriendList() {
		log.info("getFriednList");
	}

//	@GetMapping("/login")
//	public void login(HttpServletRequest req) {
//		log.info("login");
//	}

//	@PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }

	@GetMapping("/error/403")
	public void accessDeny() {
		log.info("accessDeny");
	}

}

package com.soldesk.ex01;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soldesk.ex01.domain.Board2VO;
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.AttachService;
import com.soldesk.ex01.service.Board2Service;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.service.FriendService;
import com.soldesk.ex01.service.MemberService;
import com.soldesk.ex01.util.PageMaker;
import com.soldesk.ex01.util.Pagination;

import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Log4j
public class HomeController {

//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class); // 占쌀븝옙 log4j占쏙옙 占싫억옙占쏙옙占� 占쏙옙占쏙옙求占� logging 占쏙옙占�

	@Autowired
	private MemberService memberService;

	@Autowired
	private Board2Service board2Service;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private AttachService attachService;
	
	
	
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

//	@GetMapping("board/detail")
//	@ResponseBody // JSON 占쏙옙占쏙옙占쏙옙 占쏙옙환占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙抉占� 占쌩곤옙
//	public ResponseEntity<BoardVO> boardDetail(@RequestParam Integer boardId) {
//		log.info("board controller : detail()");
//		BoardVO boardVO = boardService.selectDetail(boardId);
//		if (boardVO != null) {
//			return new ResponseEntity<>(boardVO, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 占쌔댐옙 占쌉시뱄옙占쏙옙 占쏙옙占쏙옙 占쏙옙占� 404 占쏙옙占쏙옙 占쏙옙환
//		}
//	}
	
	@GetMapping("board/detail")
	public void boardDetail(Model model, Integer boardId) {
		log.info("board controller : detail()");
		Board2VO board2VO = board2Service.selectDetail(boardId);
		model.addAttribute("board2VO", board2VO);
	}

	@GetMapping("board/regist")
	public void boardRegister() {
		log.info("board controller : registerGet()");
	}
	
//	@GetMapping("board/detail")
//	public ResponseEntity<Board2VO> boardDetail(Integer boardId) {
//		log.info("board controller : detail()");
//		Board2VO board2VO = board2Service.selectDetail(boardId);
//		return new ResponseEntity<>(board2VO,HttpStatus.OK);
//	}
	
	//占싱곤옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 
//	@GetMapping("board/list")
//	public void boardList(Model model) {
//		log.info("board controller : list()");
//		List<Board2VO> boardList = board2Service.selectList();
//
//		model.addAttribute("boardList", boardList);
//	}
	
	//占싱곤옙 占쏙옙占쏙옙 占쏙옙占쏙옙占신몌옙 占쏙옙占쏙옙징 처占쏙옙 占싼곤옙占쏙옙
	@GetMapping("board/list")
	public void list(Model model, Pagination pagination, @RequestParam int categoryId) {
			log.info("list()");
			log.info("pagination = "+pagination);
			List<Board2VO> boardList = board2Service.getPagingBoards(pagination);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setPagination(pagination);
			pageMaker.setTotalCount(board2Service.getTotalCount(categoryId));
			
			model.addAttribute("pageMaker", pageMaker);
			model.addAttribute("boardList", boardList);
		}
	
	
	
	//占쏙옙占쏙옙징 占싼곤옙 占쏟동깍옙 占싼곤옙占쏙옙
//	@GetMapping("/list")
//	public ResponseEntity<Map<String, Object>> list(Pagination pagination) {
//	    log.info("list()");
//	    log.info("pagination = " + pagination);
//
//	    List<BoardVO> boardList = boardService.getPagingBoards(pagination);
//
//	    PageMaker pageMaker = new PageMaker();
//	    pageMaker.setPagination(pagination);
//	    pageMaker.setTotalCount(boardService.getTotalCount());
//
//	    // 占쏙옙占쏙옙占싶몌옙 占쏙옙占쏙옙 Map 占쏙옙占쏙옙
//	    Map<String, Object> response = new HashMap<>();
//	    response.put("pageMaker", pageMaker);
//	    response.put("boardList", boardList);
//
//	    // ResponseEntity占쏙옙 Map占쏙옙 占쏙옙티占� 占쏙옙환
//	    return ResponseEntity.ok(response);
//	}
	
//	@GetMapping("board/list")
//	public ResponseEntity<List<Board2VO>> boardList(Model model) {
//		log.info("board controller : list()");
//		List<Board2VO> boardList = board2Service.selectList();
//
//		return new ResponseEntity<>(boardList, HttpStatus.OK);
//	}


	@GetMapping("board/update")
	public void boardUpdate(Model model, Integer boardId) {
		log.info("board controller : updateGet()");
		Board2VO board2VO = board2Service.selectDetail(boardId);
		board2VO.setAttachVO(attachService.getAttachByBoardId(boardId));
		model.addAttribute("board2VO", board2VO);
	}
	
	@GetMapping("board/search")
	public void boardSearch(Model model, @RequestParam String searchOption, @RequestParam String search) {
	    log.info("board controller: search()");
	    List<Board2VO> boardList;
	    if ("title".equals(searchOption)) {
	        boardList = board2Service.selectByTitle(search);
	    } else if ("content".equals(searchOption)) {
	        boardList = board2Service.selectByContent(search);
	    } else {
	    	boardList = new ArrayList<Board2VO>();
	    }
	    model.addAttribute("boardList", boardList);
	    
	    
	}
	
//	@GetMapping("board/update")
//	public ResponseEntity<Board2VO> boardUpdate(Integer boardId) {
//		log.info("board controller : updateGet()");
//		Board2VO board2VO = board2Service.selectDetail(boardId);
//		board2VO.setAttachVO(attachService.getAttachByBoardId(boardId));
//		return new ResponseEntity<>(board2VO,HttpStatus.OK);
//	}
//	
	

	@GetMapping("member/regist")
	public void joinMember() {
		log.info("joinMember()");
	}

	@GetMapping("member/detail")
	public void detailGet(Model model, HttpServletRequest req) {
		log.info("detailGet()");
		MemberVO memberVO = new MemberVO();
		HttpSession session = req.getSession();
		String memberId = (String) session.getAttribute("memberId");
		memberVO = memberService.getMemberById(memberId);
		log.info(memberVO);
		model.addAttribute("memberVO", memberVO);
	}

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
	
	@GetMapping("/login")
	public void login(HttpServletRequest req) {
		log.info("login");
	}
	
	@GetMapping("logout")
	public String logout(HttpServletRequest req) {
		log.info("logout()");
		HttpSession session = req.getSession();
		session.removeAttribute("memberId");

		return "redirect:/";
	}
	
	@GetMapping("/error/403")
	public void accessDeny() {
		log.info("accessDeny");
	}
	
}

package com.soldesk.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soldesk.ex01.domain.BoardVO;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.AttachService;
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

//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class); // 

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardService boardService;
	
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
	
	@GetMapping("board/detail")
	public void boardDetail(Model model, Integer boardId) {
		log.info("board controller : detail()");
		BoardVO board2VO = boardService.selectDetail(boardId);
		model.addAttribute("board2VO", board2VO);
	}

	@GetMapping("board/regist")
	public void boardRegister() {
		log.info("board controller : registerGet()");
	}
	

	

//	@GetMapping("board/list")
//	public void boardList(Model model) {
//		log.info("board controller : list()");
//		List<Board2VO> boardList = board2Service.selectList();
//
//		model.addAttribute("boardList", boardList);
//	}
	
	
	@GetMapping("board/list")
	public void list(Model model, Pagination pagination, @RequestParam int categoryId) {
			log.info("list()");
			log.info("pagination = "+pagination);
			List<BoardVO> boardList = boardService.getPagingBoards(pagination);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setPagination(pagination);
			pageMaker.setTotalCount(boardService.getTotalCount(categoryId));
			
			model.addAttribute("pageMaker", pageMaker);
			model.addAttribute("boardList", boardList);
		}
	
	
	

//	@GetMapping("board/list")
//	   public ResponseEntity<Map<String, Object>> list(Pagination pagination	) {
//	       log.info("list()");
//	       log.info("pagination = " + pagination);
//
//	       List<Board2VO> boardList = board2Service.getPagingBoards(pagination);
//
//	       PageMaker pageMaker = new PageMaker();
//	       pageMaker.setPagination(pagination);
//	       pageMaker.setTotalCount(board2Service.getTotalCount(pagination.getCategoryId()));
//	       
//	       Map<String, Object> response = new HashMap<>();
//	       response.put("pageMaker", pageMaker);
//	       response.put("boardList", boardList);
//
//	       
//	       return new ResponseEntity<>(response,HttpStatus.OK);
//	   }
	
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
		BoardVO boardVO = boardService.selectDetail(boardId);
		boardVO.setAttachVO(attachService.getAttachByBoardId(boardId));
		model.addAttribute("board2VO", boardVO);
	}
	
	@GetMapping("board/search")
	public void boardSearch(Model model, @ModelAttribute Pagination pagination, @RequestParam String searchOption, @RequestParam String search, @RequestParam int categoryId) {
	    log.info("board controller: search()");
	    List<BoardVO> boardList;
	    PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
	    if ("title".equals(searchOption)) {
	        boardList = boardService.selectByTitle(search,categoryId, pagination);
	        pageMaker.setTotalCount(boardService.searchTotalCountByTitle(categoryId, search));
	        
	        
	    } else if ("content".equals(searchOption)) {
	        boardList = boardService.selectByContent(search,categoryId,pagination);
	        pageMaker.setTotalCount(boardService.searchTotalCountByTitle(categoryId, search));
	        
	    } else {
	    	boardList = new ArrayList<BoardVO>();
	    }	
		
	    model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardList", boardList);
	}
	
	
	//비동기용 search 동기에선 잘 됬으니 안되면 말씀하세요
//	@GetMapping("board/search")
//	public ResponseEntity<Map<String, Object>> boardSearch(@ModelAttribute Pagination pagination, @RequestParam String searchOption, @RequestParam String search, @RequestParam int categoryId) {
//	    log.info("board controller: search()");
//	    List<BoardVO> boardList;
//	    PageMaker pageMaker = new PageMaker();
//		pageMaker.setPagination(pagination);
//	    if ("title".equals(searchOption)) {
//	        boardList = boardService.selectByTitle(search,categoryId, pagination);
//	        pageMaker.setTotalCount(boardService.searchTotalCountByTitle(categoryId, search));
//	    } else if ("content".equals(searchOption)) {
//	        boardList = boardService.selectByContent(search,categoryId,pagination);
//	        pageMaker.setTotalCount(boardService.searchTotalCountByTitle(categoryId, search));
//	    } else {
//	    	boardList = new ArrayList<BoardVO>();
//	    }	
//		
//	    Map<String, Object> response = new HashMap<>();
//		response.put("pageMaker", pageMaker);
//		response.put("boardList", boardList);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
	
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

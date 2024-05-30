package com.soldesk.ex01;

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
import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.service.FriendService;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
@Log4j
public class HomeController {

//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class); // 롬복 log4j를 안쓸경우 사용하는 logging 방법

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FriendService friendService;
	
	
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

	@GetMapping("board/detail")
	@ResponseBody // JSON 데이터 반환을 위한 어노테이션 추가
	public ResponseEntity<BoardVO> boardDetail(@RequestParam Integer boardId) {
		log.info("board controller : detail()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		if (boardVO != null) {
			return new ResponseEntity<>(boardVO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 해당 게시물이 없을 경우 404 응답 반환
		}
	}

	@GetMapping("board/regist")
	public void boardRegister() {
		log.info("board controller : registerGet()");
	}

	@GetMapping("board/list")
	public ResponseEntity<List<BoardVO>> list() {
		List<BoardVO> boardList = boardService.selectList();
		return new ResponseEntity<>(boardList, HttpStatus.OK);
	}

	@GetMapping("board/update")
	public void boardUpdate(Model model, Integer boardId) {
		log.info("board controller : updateGet()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		model.addAttribute("boardVO", boardVO);
	}

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

	@GetMapping("member/checkout")
	public String memberCheckout(HttpServletRequest req) {
		log.info("memberCheckout()");
		HttpSession session = req.getSession();
		session.removeAttribute("memberId");

		return "redirect:/";
	}
	
	@GetMapping("member/friendList")
	public void getFriendList(Model model, HttpServletRequest req) throws JsonProcessingException {
		HttpSession session = req.getSession();
		List<FriendVO> friendList = friendService.friendList((String)session.getAttribute("memberId"));
		ObjectMapper objectMapper = new ObjectMapper();
	    String friendListJson = objectMapper.writeValueAsString(friendList);
	    model.addAttribute("friendList", friendListJson);
	}
	
}

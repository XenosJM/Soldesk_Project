package com.soldesk.ex01;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.BoardService;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Log4j
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
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
	public void boardDetail(Model model, Integer boardId) {
		log.info("board controller : detail()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		model.addAttribute("boardVO", boardVO);
	}
	
	@GetMapping("board/regist")
	public void boardRegister() {
		log.info("board controller : registerGet()");
	}
	
	@GetMapping("board/list")
	public void boardList(Model model) {
		log.info("board controller : list()");
		List<BoardVO> boardList = boardService.selectList();

		model.addAttribute("boardList", boardList);
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
		String memberId = (String)session.getAttribute("memberId");
		memberVO = memberService.getMemberById(memberId);
		log.info(memberVO);
		model.addAttribute("memberVO", memberVO);
	}
	
	@GetMapping("member/update")
	public void updateGet() {
		log.info("updateGet()");
	}
	
	@GetMapping("member/findIdPw")
	public void findIdPw () {
		log.info("findIdPw()");
	}
}

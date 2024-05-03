package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soldesk.ex01.domain.BoardVO;
import com.soldesk.ex01.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller 
@RequestMapping(value="/board")
@Log4j
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("board controller : list()");
		List<BoardVO> boardList = boardService.selectList();
		
		model.addAttribute("boardList",boardList);
	}
	
	@GetMapping("/regist")
	public void registerGet() {
		log.info("board controller : registerGet()");
	}
	
	@PostMapping("/regist")
	public String registerPost(BoardVO vo, RedirectAttributes reAttr) {
		log.info("board controller : registerPost()");
		log.info("board controller : BoardVO ="+vo);
		int result = boardService.insertBoard(vo);
		log.info(result+"행 수정");
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public void detail(Model model, Integer boardId) {
		log.info("board controller : detail()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		model.addAttribute("boardVO", boardVO);
	}
	
	@GetMapping("/update")
	public void updateGet(Model model, Integer boardId) {
		log.info("board controller : updateGet()");
		BoardVO boardVO = boardService.selectDetail(boardId);
		model.addAttribute("boardVO", boardVO);
	}
	
	@PostMapping("/update")
	public String updatePost(BoardVO vo, RedirectAttributes reAttr) {
		log.info("board controller : updatePost()");
		int result = boardService.updateBoard(vo);
		log.info(result+"행 수정");
		return "redirect:/board/list";
	}
	
	@PostMapping("/delete")
	public String delete(Integer boardId, RedirectAttributes reAttr) {
		log.info("board controller : deletePost()");
		int result = boardService.deleteBoard(boardId);
		log.info(result + "행 삭제");
		return "redirect:/board/list";
	}
	
	
}

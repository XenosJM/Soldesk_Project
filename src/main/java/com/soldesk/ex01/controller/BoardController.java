package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		log.info("list()");
		List<BoardVO> boardList = boardService.selectList();
		
		model.addAttribute("boardList",boardList);
	}
	
}

package com.soldesk.ex01.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardVO {
	private int boardId;
	private int categoryId;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private Date boardRegistDate;
	private int boardReplyCount;
	private int attachId;
	
	@Setter
	@Getter
	private AttachVO[] attachVO; 
	
	@Setter
	@Getter
	private RecommendVO recomenndVO;
}
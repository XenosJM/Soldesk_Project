package com.soldesk.ex01.domain;

import java.util.Date;

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
public class Board2VO {
	private int boardId;
	private int categoryId;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private Date boardRegistDate;
	private int boardReplyCount;
	private int attachId;
	private MultipartFile file;
}
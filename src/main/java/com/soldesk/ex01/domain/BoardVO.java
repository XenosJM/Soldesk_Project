package com.soldesk.ex01.domain;

import java.util.Date;

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
	private int memberId;
	private String boardTitle;
	private String boardContent;
	private Date boardRegistDate;
	private int boardReplyCount;
}

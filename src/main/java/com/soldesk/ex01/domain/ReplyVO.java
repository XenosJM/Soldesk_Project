package com.soldesk.ex01.domain;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyVO {
	private int replyId;
	private int boardId;
	private int categoryId;
	private int memberId;
	private String replyContent;
	private Date replyRegistDate;
	
}

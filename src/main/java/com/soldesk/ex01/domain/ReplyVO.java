package com.soldesk.ex01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyVO {
	private int replyId;
	private int boardId;
	private int categoryId;
	private String memberId;
	private String replyContent;
	private Date replyRegistDate;
	
}

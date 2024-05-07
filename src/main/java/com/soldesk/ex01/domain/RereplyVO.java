package com.soldesk.ex01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RereplyVO {
	private int rereplyId;
	private int replyId;
	private int memberId;
	private String rereplyContent;
	private Date rereplyRegistDate;
}

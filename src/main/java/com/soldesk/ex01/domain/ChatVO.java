package com.soldesk.ex01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatVO {
	
	private int chatId;
	private String memberId;
	private String chatContent;
	private Date chatDate;
	private int chatGroupId;
	private String chatMemberId;
	
}

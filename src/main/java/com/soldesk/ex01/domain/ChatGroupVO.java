package com.soldesk.ex01.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroupVO {
	
	private int chatGroupId;
	private String groupTitle;
	private String[] chatMember;
	
	public String getChatMember() {
		return Arrays.toString(chatMember);
	}
}

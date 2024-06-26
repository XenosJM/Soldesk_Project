package com.soldesk.ex01.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FriendVO {
	
	private int friendshipId;
	private String memberId;
	private String friendMemberId;
	private String friendState;
	private Date friendshipDate;
}

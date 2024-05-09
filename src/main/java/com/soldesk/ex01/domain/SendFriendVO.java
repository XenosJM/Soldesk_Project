package com.soldesk.ex01.domain;

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
public class SendFriendVO {
	private int sendFriendId;
	private int memberId;
	private int receiveMemberId;
	
	

}
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
public class MemberVO {
	private int memberId;
	private String memberName;
	private String memberPassword;
	private int managerId;
	private String memberEmail;
	private String memberProperty;
	private Date memberRegistDate;
}

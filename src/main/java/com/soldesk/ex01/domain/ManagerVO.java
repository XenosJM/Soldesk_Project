package com.soldesk.ex01.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ManagerVO {
	
	private int managerId;
	private int memberId;
	private int categoriId;
}

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
public class ManagerVO {
	
	private int managerId;
	private int memberId;
	private int categoryId;
}

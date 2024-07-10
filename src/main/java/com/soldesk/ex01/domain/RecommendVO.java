package com.soldesk.ex01.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecommendVO {
	private int recommendId;
	private int boardId;	
	private String[] recommendMember;
	private String recommendMemberString;
	
	public String getRecommendMemberAsString() {
		return Arrays.toString(recommendMember);
	}
	
		
}

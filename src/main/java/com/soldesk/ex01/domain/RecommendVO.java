package com.soldesk.ex01.domain;

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
	private int recommend;
	private int decommend;
}

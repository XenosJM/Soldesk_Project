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
public class RequestVO {
	private int requestId;
	// 친구 추가 요청을 보내는 사람 id
	private int memberId;
	// 요청을 보낼 id
	private int receiverId;
	// 현재 상태(대기중, 수락, 거절 등)
	private String requestState;
	private Date requestDate;
	

}

/* 친구 요청 보내기
 * 요청한 멤버와 요청을 받을 멤버 아이디
 * 자기 자신에게 친구요청은 불가능
 * 친구요청은 한명이 여러명에게 또는 여러명이 한명에게 발생이 가능하다.
 * 친구 요청을 수락 또는 거절이 가능하며
 * 요청받은자가 
 * 수락시 친구목록에 등록됨
 * 거절시 요청이 삭제 됨
 * 
 */
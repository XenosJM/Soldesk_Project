package com.soldesk.ex01.domain;

import java.time.LocalDate;

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
public class ReceiveVO {
	
	private int receiveId;
	// 친구추가 요청을 받은 사람의 id
	private String memberId;
	// 친구요청을 보낸 사람의 id
	private String requesterId;
	// 현재 상태(대기중, 수락, 거절 등)
	private String receiveState;
	private LocalDate receiveDate;
	
}
/* 친구 요청 받음
 * 누구로부터 누구에게(친구목록을 보는사람) 왔는지
 * 요청을 수락할지 거절할지 선택의 결과 값을 저장
 * 
 */

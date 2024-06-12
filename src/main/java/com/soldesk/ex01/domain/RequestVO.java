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
public class RequestVO {
	private int requestId;
	// ģ�� �߰� ��û�� ������ ��� id
	private String memberId;
	// ��û�� ���� id
	private String receiverId;
	// ���� ����(�����, ����, ���� ��)
	private String requestState;
	private LocalDate requestSendDate;
	

}

/* ģ�� ��û ������
 * ��û�� ����� ��û�� ���� ��� ���̵�
 * �ڱ� �ڽſ��� ģ����û�� �Ұ���
 * ģ����û�� �Ѹ��� �������� �Ǵ� �������� �Ѹ��� �߻��� �����ϴ�.
 * ģ�� ��û�� ���� �Ǵ� ������ �����ϸ�
 * ��û�����ڰ� 
 * ������ ģ����Ͽ� ��ϵ�
 * ������ ��û�� ���� ��
 * 
 */
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
	// ģ�� �߰� ��û�� ������ ��� id
	private int memberId;
	// ��û�� ���� id
	private int receiverId;
	// ���� ����(�����, ����, ���� ��)
	private String requestState;
	private Date requestDate;
	

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
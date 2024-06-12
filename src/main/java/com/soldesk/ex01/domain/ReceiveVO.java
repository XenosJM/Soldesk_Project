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
	// ģ���߰� ��û�� ���� ����� id
	private String memberId;
	// ģ����û�� ���� ����� id
	private String requesterId;
	// ���� ����(�����, ����, ���� ��)
	private String receiveState;
	private LocalDate receiveDate;
	
}
/* ģ�� ��û ����
 * �����κ��� ��������(ģ������� ���»��) �Դ���
 * ��û�� �������� �������� ������ ��� ���� ����
 * 
 */

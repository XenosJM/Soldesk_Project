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
public class ReceiveVO {
	
	private int receiveId;
	// ģ���߰� ��û�� ���� ����� id
	private int memberId;
	// ģ����û�� ���� ����� id
	private int requesterId;
	// ���� ����(�����, ����, ���� ��)
	private String receiveState;
	private Date receiveDate;
	
}
/* ģ�� ��û ����
 * �����κ��� ��������(ģ������� ���»��) �Դ���
 * ��û�� �������� �������� ������ ��� ���� ����
 * 
 */

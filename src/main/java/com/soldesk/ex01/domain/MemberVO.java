package com.soldesk.ex01.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String memberId;
	private String memberPassword;
	private int roleId;
	private String memberEmail;
	private Integer[] memberProperty;
	private Date memberRegistDate;
	private String memberPropertyAsString;
	
	// �迭�� memberProperty�� ���� �����ö� ���
	public String getMemberPropertyAsString() {
        return Arrays.toString(memberProperty);
    }
	
	// memberProperty�� ���� ���ڿ��� ��ȯ�ѵ� ������ ������ DB���� �� �������� ��
	public void setMemberPropertyAsString(String memberPropertyAsString) {
		// ���ڿ� property�� �ΰ��� �ƴϰ� ������� �ʴٸ� ����.
	    if (memberPropertyAsString != null && !memberPropertyAsString.isEmpty()) {
	        // ���ȣ ����,  "\\[|\\]" = [ �Ǵ� ]�� ã�Ƽ� �������� ��ȯ(����)
	        memberPropertyAsString = memberPropertyAsString.replaceAll("\\[|\\]", "");
	        
	        if(memberPropertyAsString.isEmpty()) {
	        	this.memberProperty = new Integer[0];
	        	return;
	        }
	        // , �� �и��Ͽ� ���ڿ� �迭�� ��ȯ�Ͽ� ���ڿ� �迭 ������ ����
	        String[] propertyString = memberPropertyAsString.split(",");
	        // ���ڿ� �迭�� propertyString�� int�� �Ľ�
	        this.memberProperty = new Integer[propertyString.length];
	        for (int i = 0; i < propertyString.length; i++) {
	        	// trim()���� ���� ���� �� ��ȯ
	        	this.memberProperty[i] = Integer.parseInt(propertyString[i].trim());
	        }
	    } else {
	    	this.memberProperty = new Integer[0];
	    }
	} // end setMemberPropertyAsString()
} // end MemberVO

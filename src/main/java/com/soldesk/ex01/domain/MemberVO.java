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
	
	// 배열인 memberProperty의 값을 가져올때 사용
	public String getMemberPropertyAsString() {
        return Arrays.toString(memberProperty);
    }
	
	// memberProperty의 값을 문자열로 변환한뒤 저장한 값으로 DB에는 이 변수값이 들어감
	public void setMemberPropertyAsString(String memberPropertyAsString) {
		// 문자열 property가 널값이 아니고 비어있지 않다면 실행.
	    if (memberPropertyAsString != null && !memberPropertyAsString.isEmpty()) {
	        // 대괄호 제거,  "\\[|\\]" = [ 또는 ]를 찾아서 공백으로 전환(제거)
	        memberPropertyAsString = memberPropertyAsString.replaceAll("\\[|\\]", "");
	        
	        if(memberPropertyAsString.isEmpty()) {
	        	this.memberProperty = new Integer[0];
	        	return;
	        }
	        // , 로 분리하여 문자열 배열로 변환하여 문자열 배열 변수에 저장
	        String[] propertyString = memberPropertyAsString.split(",");
	        // 문자열 배열인 propertyString을 int로 파싱
	        this.memberProperty = new Integer[propertyString.length];
	        for (int i = 0; i < propertyString.length; i++) {
	        	// trim()으로 공백 제거 후 변환
	        	this.memberProperty[i] = Integer.parseInt(propertyString[i].trim());
	        }
	    } else {
	    	this.memberProperty = new Integer[0];
	    }
	} // end setMemberPropertyAsString()
} // end MemberVO

package com.soldesk.ex01.domain;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroupVO {
	
	private int chatGroupId;
	private String groupTitle;
	private String[] chatMember;
	private String chatMemberAsString;
	
	  // chatMember 배열을 JSON 형식 문자열로 변환하여 반환
    public String getChatMemberAsString() {
        if (chatMember != null && chatMember.length > 0) {
            return Arrays.toString(chatMember)
                         .replaceAll("^\\[|\\]$", ""); // 대괄호 제거
        }
        return "";
    }

    // chatMemberAsString 문자열을 배열로 변환하여 chatMember에 저장
    public void setChatMemberAsString(String chatMemberAsString) {
        if (chatMemberAsString != null && !chatMemberAsString.isEmpty()) {
            String[] members = chatMemberAsString.split(",");
            this.chatMember = new String[members.length];
            for (int i = 0; i < members.length; i++) {
                this.chatMember[i] = members[i].trim(); // 공백 제거 후 변환
            }
        } else {
            this.chatMember = new String[0];
        }
    }

}

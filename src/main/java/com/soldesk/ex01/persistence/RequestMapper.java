package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.RequestVO;

@Mapper
public interface RequestMapper {

	int insertRequest(RequestVO requestVO);
	List<RequestVO> sendListRequest(String memberId);
	int selectRequestByReceiverId(String receiverId);
	RequestVO selectByReceiverId(String receiverId);
	RequestVO selectByRequestId(int requestId);
	List<RequestVO> allSendList();
	int requestStateChange(@Param("requestId")int requestId, @Param("requestState")String requestState);
	int cancelRequest(int requestId);

}

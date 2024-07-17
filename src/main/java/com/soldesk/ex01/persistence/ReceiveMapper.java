package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.soldesk.ex01.domain.ReceiveVO;

@Mapper
public interface ReceiveMapper {
	
	int insertReceive(ReceiveVO receiveVO);
	List<ReceiveVO> receiveListRequest(String memberId);
	ReceiveVO selectByRequesterId(String requesterId);
	ReceiveVO selectByReceiveId(int receiveId);
	int receiveStateChange(@Param("receiveId")int receiveId, @Param("receiveState")String receiveState);
	List<ReceiveVO> allReceiveList();
	int rejectRequest(int receiveId);

}

package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.RereplyVO;

@Mapper
public interface RereplyMapper {
	int insertRereply(RereplyVO vo);
	List<RereplyVO>selectRereply(int replyId);
	int updateRereply(RereplyVO vo);
	int deleteRereply(int rereplyId);
	int deleteRereplyToReply(int replyId);
	RereplyVO findRereply(int rereplyId);
}

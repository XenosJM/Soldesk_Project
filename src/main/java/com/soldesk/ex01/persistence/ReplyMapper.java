package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.ReplyVO;

@Mapper
public interface ReplyMapper {
	int insertReply(ReplyVO vo);
	List<ReplyVO> selectReplyMemberList(ReplyVO vo);
	List<ReplyVO> selectReplyContentList(ReplyVO vo);
	int updateReply(ReplyVO vo);
	int deleteReply(int replyId);
}

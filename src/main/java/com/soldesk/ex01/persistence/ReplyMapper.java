package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.ReplyVO;

@Mapper
public interface ReplyMapper {
	int insertReply(ReplyVO vo);
	List<ReplyVO> selectReplyMemberList(String memberId);
	List<ReplyVO> selectReplyContentList(String Content);
	List<ReplyVO> selectReplyBoard(int boardId);
	int updateReply(ReplyVO vo);
	int deleteReply(int replyId);
}

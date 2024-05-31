package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.AttachVO;

@Mapper
public interface AttachMapper {
    int insert(AttachVO attachVO);
    AttachVO selectByAttachId(int boardId);
    List<Integer> selectIdList();
    int update(AttachVO attach);
    int delete(int boardId);
    AttachVO selectByBoardId(int boardId);
}
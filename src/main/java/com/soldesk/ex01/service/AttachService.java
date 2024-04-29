package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.AttachVO;

public interface AttachService {
   
    int createAttach(AttachVO attachVO);
    AttachVO getAttachById(int attachId);
    List<Integer> getAllId();
    int updateAttach(AttachVO attachVO);
    int deleteAttach(int attachId);

}
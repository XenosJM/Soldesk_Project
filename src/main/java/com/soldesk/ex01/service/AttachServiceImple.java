package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.persistence.AttachMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AttachServiceImple implements AttachService {

   @Autowired
    private AttachMapper attachMapper;


    @Override
    public int createAttach(AttachVO attachVO) {
       log.info("createAttach");
       log.info(attachVO);
        return attachMapper.insert(attachVO);
    }

    @Override
    public AttachVO getAttachById(int attachId) {
       log.info("getAttachById()");
        return attachMapper.selectByAttachId(attachId);
    }
    
    @Override
    public List<Integer> getAllId() {
       log.info("getAllId()");
       return attachMapper.selectIdList();
    }

    
    @Override
    public int deleteAttach(int boardId) {
       log.info("deleteAttach()");	
        return attachMapper.delete(boardId);
    }

	@Override
	public AttachVO[] getAttachByBoardId(int boardId) {
		log.info("getAttachByBoardId()");
		return attachMapper.selectByBoardId(boardId);
	}

	@Override
	public int updateAttach(AttachVO attachvo) {
		return attachMapper.updateAttach(attachvo);
	}
    
    
}
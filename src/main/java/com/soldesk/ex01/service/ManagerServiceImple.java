package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.ManagerVO;
import com.soldesk.ex01.persistence.ManagerMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ManagerServiceImple implements ManagerService {

	@Autowired
	private ManagerMapper managerMapper;
	
	@Override
	public int createManager(ManagerVO managerVO) {
		log.info("createManager()");
		int result = managerMapper.insert(managerVO);
		return result;
	}

	@Override
	public ManagerVO getManagerById(int managerId) {
		log.info("getManagerById");
		return managerMapper.selectByManagerId(managerId);
	}

	@Override
	public List<ManagerVO> getAllManager() {
		log.info("getAllManager()");
		return managerMapper.selectAllManager();
	}

	@Override
	public int changeManager(ManagerVO managerVO) {
		log.info("changeManager");
		int result = managerMapper.update(managerVO);
		return result;
	}

	@Override
	public int deleteManager(int managerId) {
		log.info("deleteManager");
		return managerMapper.delete(managerId);
	}

}

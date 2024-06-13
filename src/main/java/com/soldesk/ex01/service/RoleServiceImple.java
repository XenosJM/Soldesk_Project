package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.RoleVO;
import com.soldesk.ex01.persistence.RoleMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RoleServiceImple implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public int createManager(RoleVO managerVO) {
		log.info("createManager()");
		int result = roleMapper.insert(managerVO);
		return result;
	}

//	@Override
//	public List<ManagerVO> getAllManager() {
//		log.info("getAllManager()");
//		return managerMapper.selectAllManager();
//	}

	@Override
	public int deleteManager(int managerId) {
		log.info("deleteManager");
		return roleMapper.delete(managerId);
	}

}

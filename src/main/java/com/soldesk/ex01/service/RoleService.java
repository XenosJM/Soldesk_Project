package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.RoleVO;

public interface RoleService {
	
	int createManager();
//	List<ManagerVO> getAllManager();
	int deleteManager(int managerId);
}

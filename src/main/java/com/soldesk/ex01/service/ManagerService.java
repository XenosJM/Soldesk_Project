package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.ManagerVO;

public interface ManagerService {
	
	int createManager(ManagerVO managerVO);
	ManagerVO getManagerById(int managerId);
	List<ManagerVO> getAllManager();
	int changeManager(int managerId);
	int deleteManager(int managerId);
}

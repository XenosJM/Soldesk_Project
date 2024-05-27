package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.ManagerVO;

@Mapper
public interface ManagerMapper {
	int insert(ManagerVO managerVO);
	ManagerVO selectByManagerId(int managerId);
	List<ManagerVO> selectAllManager();
	int update(int managerId, String memberId);
	int delete(int managerId);
}

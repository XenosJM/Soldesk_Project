package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.RoleVO;

@Mapper
public interface RoleMapper {
	int insert(RoleVO managerVO);
//	List<ManagerVO> selectAllManager();
	int delete(int managerId);
}

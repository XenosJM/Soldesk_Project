package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.CategoriVO;

@Mapper
public interface CategoriMapper {
	int insert(CategoriVO vo);
	List<CategoriVO> selectCategoriList();
	List<CategoriVO> selectCategoriTitle(CategoriVO vo);
	int updateCategoriTitle(CategoriVO vo);
	int delete(CategoriVO vo);
}

package com.soldesk.ex01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.JwtTokenDTO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.jwt.JwtTokenProvider;
import com.soldesk.ex01.persistence.FriendMapper;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Transactional
	@Override
	public int createMember(MemberVO memberVO) {
		log.info("createMember()");
		String encodedPassword = encoder.encode(memberVO.getMemberPassword());
		memberVO.setMemberPassword(encodedPassword);
		return memberMapper.insert(memberVO);
	}
	
	@PostAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public MemberVO getMemberById(String memberId) {
		log.info("getMemberById()");
		return memberMapper.selectByMemberId(memberId);
	}

	@Override
	public List<MemberVO> getAllMember() {
		log.info("getAllMember()");
		return memberMapper.selectIdList();
	}

//	@Transactional
//	@Override
//	public int updateMember(MemberVO memberVO) {
//		log.info("updateMember()");
//		String encodedPassword = encoder.encode(memberVO.getMemberPassword());
//		memberVO.setMemberPassword(encodedPassword);
//		return memberMapper.update(memberVO);
//	}

	@Transactional
	@PreAuthorize("isAuthenticated() and (#memberVO.memberId == principal.username)")
	@Override
	public int updateMemberPermission(MemberVO memberVO) {
		log.info("updateMemberPermission()");
		int result = memberMapper.updateManager(memberVO);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() and (#memberVO.memberId == principal.username)")
	@Override
	public int updateMemberProperty(MemberVO memberVO) {
		log.info("updateMemberProperty()");
		
		// ���޹��� ������ ��ǰ �迭�� �� ���� �����ϱ����� Array�迭 ����
		List<Integer> deletePropertyList = new ArrayList<>();
		for(int deleteItem : memberVO.getMemberProperty()) {
			deletePropertyList.add(deleteItem);
		}
		// ���� ȸ���� ��ǰ �迭�� �����ϱ����� Array�迭 ����
		MemberVO checkVO = memberMapper.selectByMemberId(memberVO.getMemberId());
		List<Integer> originalPropertyList = new ArrayList<>();
		for(int originalItem : checkVO.getMemberProperty()) {
			originalPropertyList.add(originalItem);
		}
		// Iterator�� ����Ͽ� ���� �迭���� ������ �����۰� ���� ������ ����
		Iterator<Integer> itr = originalPropertyList.iterator();
		while (itr.hasNext()) { // itr�� ���� ���� �����ϸ� ��
	        Integer item = itr.next(); // ������� ��������
	        if (deletePropertyList.contains(item)) {
	            itr.remove();
	        }
	    }
		Integer[] propertyArray = originalPropertyList.toArray(new Integer[0]);
		checkVO.setMemberProperty(propertyArray);
		int result = memberMapper.updateProperty(checkVO);
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() and (#memberId == principal.username)")
	@Override
	public int deleteMember(String memberId) {
		log.info("deleteMember()");
		return memberMapper.delete(memberId);
	}

	
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#memberVO.memberId == principal.username)")
	@Override
	public int updatePassword(MemberVO memberVO) {
		log.info("updatePw");
		String encodedPassword = encoder.encode(memberVO.getMemberPassword());
		memberVO.setMemberPassword(encodedPassword);
		int result = memberMapper.updatePassword(memberVO);
		return result;
	}
	
	@Transactional
	@PreAuthorize("isAuthenticated() and (#memberVO.memberId == principal.username)")
	@Override
	public int updateEmail(MemberVO memberVO) {
		log.info("updateEamil");
		int result = memberMapper.updateEmail(memberVO);
		return result;
	}

	@Override
	public int checkPassword(MemberVO memberVO) {
		log.info("checkPW");
		int result = 0; 
		MemberVO checkVO = memberMapper.memberCheck(memberVO.getMemberId());
		String password = encoder.encode(checkVO.getMemberPassword());
		
		if(checkVO != null && encoder.matches(password, memberVO.getMemberPassword())) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	@Transactional
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_HEAD_MANAGER')")
	@Override
	public int memberRoleUpdate(MemberVO memberVO) {
		int result = memberMapper.updateRole(memberVO);
		return result;
	}

   @Override
   public List<MemberVO> findMemberId(String memberId) {
      List<MemberVO> list = memberMapper.findMemberId(memberId);
      return list;
   }

}

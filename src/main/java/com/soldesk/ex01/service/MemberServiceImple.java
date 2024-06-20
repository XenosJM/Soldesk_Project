package com.soldesk.ex01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.MemberVO;
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
		
		// 전달받은 삭제할 상품 배열의 각 값에 접근하기위한 Array배열 선언
		List<Integer> deletePropertyList = new ArrayList<>();
		for(int deleteItem : memberVO.getMemberProperty()) {
			deletePropertyList.add(deleteItem);
		}
		// 원래 회원의 상품 배열에 접근하기위한 Array배열 선언
		MemberVO checkVO = memberMapper.selectByMemberId(memberVO.getMemberId());
		List<Integer> originalPropertyList = new ArrayList<>();
		for(int originalItem : checkVO.getMemberProperty()) {
			originalPropertyList.add(originalItem);
		}
		// Iterator를 사용하여 원래 배열에서 삭제할 아이템과 같은 아이템 제거
		Iterator<Integer> itr = originalPropertyList.iterator();
		while (itr.hasNext()) { // itr에 다음 값이 존재하면 참
	        Integer item = itr.next(); // 다음요소 가져오기
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

	@Override
	public int memberCheck(Map<String, String> res, HttpSession session) {
		log.info("memberCheck()");
		String memberId = res.get("memberId");
        String memberPassword = res.get("memberPassword");
        MemberVO memberVO = memberMapper.memberCheck(memberId);

        if (memberVO != null && memberPassword.equals(memberVO.getMemberPassword())) {
            return 1;
        } else {
            return 0;
        }
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

}

package com.soldesk.ex01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService{
	
	@Autowired
	public MemberMapper memberMapper;
	
	@Transactional
	@Override
	public int createMember(MemberVO memberVO) {
		log.info("createMember()");
		int result = memberMapper.insert(memberVO);
		return result;
	}

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

	@Transactional
	@Override
	public int updateMember(MemberVO memberVO) {
		log.info("updateMember()");
		return memberMapper.update(memberVO);
	}

	@Transactional
	@Override
	public int updateMemberPermission(MemberVO memberVO) {
		log.info("updateMemberPermission()");
		int result = memberMapper.updateManager(memberVO);
		return result;
	}

	@Transactional
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
//		// ������ �����۰� ���� �������� ���� �迭���� ����
//		for(int i = 0; i < checkVO.getMemberProperty().length; i++) {
//			
//			if(originalPropertyList.get(i).equals(deletePropertyList.get(i))) {
//				deletePropertyList.remove(i);
//			}
//		}
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
		
//		// �����迭�� ����Ʈ�� ��ȯ
//		List<Integer> list = new ArrayList<>(Arrays.asList(propertyArray));
//		list.remove(propertyIndex);
//		// ����Ʈ�� �迭�� ��ȯ
//		propertyArray = list.toArray(new Integer[0]);
//		
//		if(propertyArray.length == 0 || propertyArray == null) {
//			// �迭�� ����ְų� ������
//			memberVO.setMemberProperty(new Integer[0]);
//		} else {
//			// �迭�� ���� �ִٸ�.
//			memberVO.setMemberProperty(propertyArray);
//		}
		int result = memberMapper.updateProperty(memberVO);
		return result;
	}

	@Transactional
	@Override
	public int deleteMember(String memberId) {
		log.info("deleteMember()");
		return memberMapper.delete(memberId);
	}

	@Override
	public MemberVO memberCheck(String memberId) {
		log.info("memberCheck()");
		return memberMapper.memberCheck(memberId);
	}

	@Override
	public MemberVO findId(String memberEmail) {
		return memberMapper.findId(memberEmail);
	}

	@Override
	public int updatePassword(MemberVO memberVO) {
		int result = memberMapper.updatePassword(memberVO);
		return result;
	}

	@Override
	public int updateEmail(MemberVO memberVO) {
		int result = memberMapper.updateEmail(memberVO);
		return result;
	}

}

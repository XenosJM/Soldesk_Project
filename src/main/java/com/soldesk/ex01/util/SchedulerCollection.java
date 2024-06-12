package com.soldesk.ex01.util;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.domain.ReceiveVO;
import com.soldesk.ex01.domain.RequestVO;
import com.soldesk.ex01.persistence.AttachMapper;
import com.soldesk.ex01.persistence.FriendMapper;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class SchedulerCollection {

	@Autowired
	private String uploadPath;

	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private FriendMapper friend;
	

//	@Scheduled(fixedRate = 5000)
//	public void scheduletest() {
//		log.info("test");
//		List<AttachVO> attachList = attachMapper.selectYesterday();
//		log.info(attachList);
//		if (attachList.size() == 0) {
//			return;
//		}
//
//		// ���� �������� ���� �̸��� �����Ͽ� List<String>���� ����
//		List<String> savedList = attachList.stream() // ������ ó�� ��� ����� ���� stream ����
//				.map(this::toChgName) // attach�� attach.getAttachChgName()���� ����
//				.collect(Collectors.toList()); // stream�� list�� ����
//		log.info(savedList);
//		// ���� ��¥���� 1�� �� ���ε� ���� ��� ����
//		File targetDir = Paths.get(uploadPath, attachList.get(0).getAttachPath()).toFile();
//
//		// ���ε� ������ ����� ���� ��� ��
//		// savedList�� ���� �̸��� ���� ��츸 ��ȸ
//		File[] removeFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
//		log.info(removeFiles);
//		for (File file : removeFiles) {
//			log.warn(file.getAbsolutePath());
//			file.delete(); // ���� ����
//		}
//
//	}

//	@Scheduled(cron = "0 0 1 * * ?")
//	public void localAttachDelete() {
//	
//		List<AttachVO> attachList = attachMapper.selectYesterday();
//		log.info(attachList);
//		if (attachList.size() == 0) {
//			return;
//		}
//
//		// ���� �������� ���� �̸��� �����Ͽ� List<String>���� ����
//		List<String> savedList = attachList.stream() // ������ ó�� ��� ����� ���� stream ����
//				.map(this::toChgName) // attach�� attach.getAttachChgName()���� ����
//				.collect(Collectors.toList()); // stream�� list�� ����
//		log.info(savedList);
//		// ���� ��¥���� 1�� �� ���ε� ���� ��� ����
//		File targetDir = Paths.get(uploadPath, attachList.get(0).getAttachPath()).toFile();
//
//		// ���ε� ������ ����� ���� ��� ��
//		// savedList�� ���� �̸��� ���� ��츸 ��ȸ
//		File[] removeFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
//		log.info(removeFiles);
//		for (File file : removeFiles) {
//			log.warn(file.getAbsolutePath());
//			file.delete(); // ���� ����
//		}
//	}

	// attach�� ���޹޾� ���� �̸� ����
	private String toChgName(AttachVO attach) {
		return attach.getAttachChgName();
	}
//	cron = "0 0 4 1/1 * ?"
	@Scheduled(cron = "0 0 4 1/1 * ?")
	public void removeOldRequestAndReceive() {
		log.info("removeOldRequestAndReceive()");
		List<RequestVO> allRequestList = new ArrayList<>();
		allRequestList = friend.allSendList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		String nowDate = LocalDate.now().format(formatter);
		LocalDate startDate;
		LocalDate endDate = LocalDate.parse(nowDate, formatter);
		
		for(RequestVO item : allRequestList) {
			startDate = item.getRequestSendDate();
			long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
			// requestState�� �����(null)�̸鼭 7���� ���� ��û������
			if(item.getRequestState() == null && daysBetween >= 7) {
				friend.cancelRequest(item.getRequestId());
			}
		} // end forEach
		
		List<ReceiveVO> allReceiveList = new ArrayList<>();
		allReceiveList = friend.allReceiveList();
		
		for(ReceiveVO item : allReceiveList) {
			startDate = item.getReceiveDate();
			long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
			if(item.getReceiveState() == null && daysBetween >= 7) {
				friend.rejectRequest(item.getReceiveId());
			}
		} // end forEach
	} // end removeOldRequest()
	
	@Scheduled(cron = "0 0 3 1/1 * ?")
	public void removeNomoreFriend() {
		List<FriendVO> allFriendList = new ArrayList<>();
		allFriendList = friend.allFriend();
		
		// a�� b�� ģ���� ��Ȳ���� db���� a b������, b a �����Ͱ� ���ִ�. 
		// �̸� ���ؼ� �����̶� ģ�������� ���� 
		for(FriendVO item : allFriendList) {
			if(item.getFriendState() == "delete") {
				friend.deleteFriend(item.getFriendshipId());
			}
		}
		
	} // end removeNomoreFriend()

} // end SchedulerCollection

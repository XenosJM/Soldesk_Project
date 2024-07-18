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
import com.soldesk.ex01.persistence.ReceiveMapper;
import com.soldesk.ex01.persistence.RequestMapper;

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
	
	@Autowired
	private RequestMapper request;
	
	@Autowired
	private ReceiveMapper receive;
	

//	@Scheduled(fixedRate = 5000)
//	public void scheduletest() {
//		log.info("test");
//		List<AttachVO> attachList = attachMapper.selectYesterday();
//		log.info(attachList);
//		if (attachList.size() == 0) {
//			return;
//		}
//
//	
//		List<String> savedList = attachList.stream() 
//				.map(this::toChgName) 
//				.collect(Collectors.toList()); 
//		log.info(savedList);
//		
//		File targetDir = Paths.get(uploadPath, attachList.get(0).getAttachPath()).toFile();
//
//		
//		
//		File[] removeFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
//		log.info(removeFiles);
//		for (File file : removeFiles) {
//			log.warn(file.getAbsolutePath());
//			file.delete(); 
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
	
	//cron = "0 0 4 1/1 * ?"
	@Scheduled(cron = "0 0 4 * * ?")
	public void removeOldRequestAndReceive() {
		log.info("removeOldRequestAndReceive()");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		String nowDate = LocalDate.now().format(formatter);
		LocalDate startDate;
		LocalDate endDate = LocalDate.parse(nowDate, formatter);
		
		List<RequestVO> allRequestList = new ArrayList<>();
		allRequestList = request.allSendList();

		for(RequestVO item : allRequestList) {
			startDate = item.getRequestSendDate();
			long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
			// requestState가 대기중(null)이면서 7일이 지난 요청에대해
			if(item.getRequestState() == null && daysBetween >= 7) {
				request.cancelRequest(item.getRequestId());
			}
		} // end forEach
		
		List<ReceiveVO> allReceiveList = new ArrayList<>();
		allReceiveList = receive.allReceiveList();

		for(ReceiveVO item : allReceiveList) {
			startDate = item.getReceiveDate();
			long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
			if(item.getReceiveState() == null && daysBetween >= 7) {
				receive.rejectRequest(item.getReceiveId());
			}
		} // end forEach
	} // end removeOldRequest()
	
	@Scheduled(cron = "0 0 * * * ?")
	public void changedStateRemove() {
		
		List<RequestVO> allRequestList = request.allSendList();
		List<ReceiveVO> allReceiveList = receive.allReceiveList();
		
		for(RequestVO item : allRequestList) {
			String reject = "reject";
			String accept = "accept";
			// requestState가 대기중이 아니면서 reject 또는 accept인 경우
			if(item.getRequestState() != null && (item.getRequestState().equals(reject) || item.getRequestState().equals(accept))) {
				request.cancelRequest(item.getRequestId());
			}
		} // end forEach

		for(ReceiveVO item : allReceiveList) {
			String reject = "reject";
			String accept = "accept";
			if(item.getReceiveState() != null && (item.getReceiveState().equals(reject) || item.getReceiveState().equals(accept))) {
				receive.rejectRequest(item.getReceiveId());
			}
		} // end forEach
	}

	@Scheduled(cron = "0 0 3 * * ?")
	public void removeNomoreFriend() {
		List<FriendVO> allFriendList = new ArrayList<>();
		allFriendList = friend.allFriend();

		// a와 b가 친구인 상황에서 db에는 a b데이터, b a 데이터가 들어가있다. 
		// 이를 비교해서 한쪽이라도 친구삭제를 통해 
		for(FriendVO item : allFriendList) {
			if(item.getFriendState() == "delete") {
				friend.deleteFriend(item);
			}
		}

	} // end removeNomoreFriend()

} // end SchedulerCollection






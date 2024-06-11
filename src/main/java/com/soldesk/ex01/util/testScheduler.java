package com.soldesk.ex01.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.persistence.AttachMapper;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class testScheduler {

	@Autowired
	private String uploadPath;

	@Autowired
	private AttachMapper attachMapper;

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

}

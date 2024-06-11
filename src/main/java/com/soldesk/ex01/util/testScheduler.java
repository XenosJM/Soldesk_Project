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
//		// 파일 정보에서 파일 이름만 추출하여 List<String>으로 변경
//		List<String> savedList = attachList.stream() // 데이터 처리 기능 사용을 위한 stream 변경
//				.map(this::toChgName) // attach를 attach.getAttachChgName()으로 변경
//				.collect(Collectors.toList()); // stream을 list로 변경
//		log.info(savedList);
//		// 현재 날짜에서 1일 전 업로드 폴더 경로 생성
//		File targetDir = Paths.get(uploadPath, attachList.get(0).getAttachPath()).toFile();
//
//		// 업로드 폴더에 저장된 파일 목록 중
//		// savedList에 파일 이름이 없는 경우만 조회
//		File[] removeFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
//		log.info(removeFiles);
//		for (File file : removeFiles) {
//			log.warn(file.getAbsolutePath());
//			file.delete(); // 파일 삭제
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
//		// 파일 정보에서 파일 이름만 추출하여 List<String>으로 변경
//		List<String> savedList = attachList.stream() // 데이터 처리 기능 사용을 위한 stream 변경
//				.map(this::toChgName) // attach를 attach.getAttachChgName()으로 변경
//				.collect(Collectors.toList()); // stream을 list로 변경
//		log.info(savedList);
//		// 현재 날짜에서 1일 전 업로드 폴더 경로 생성
//		File targetDir = Paths.get(uploadPath, attachList.get(0).getAttachPath()).toFile();
//
//		// 업로드 폴더에 저장된 파일 목록 중
//		// savedList에 파일 이름이 없는 경우만 조회
//		File[] removeFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
//		log.info(removeFiles);
//		for (File file : removeFiles) {
//			log.warn(file.getAbsolutePath());
//			file.delete(); // 파일 삭제
//		}
//	}

	// attach를 전달받아 파일 이름 리턴
	private String toChgName(AttachVO attach) {
		return attach.getAttachChgName();
	}

}

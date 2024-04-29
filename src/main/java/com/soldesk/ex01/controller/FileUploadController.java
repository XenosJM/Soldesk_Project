package com.soldesk.ex01.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class FileUploadController {

   @Autowired
   private String uploadPath; // Bean���� ������ uploadPath() ��ü ���� 
   
   // upload.jsp ������ ȣ�� 
   @GetMapping("/upload")
   public void uploadGET() {
      log.info("uploadGET()");
   } // end uploadGET()
   
// uploads.jsp ������ ȣ�� 
   @GetMapping("/uploads")
   public void uploadsGET() {
      log.info("uploadGET()");
   } // end uploadGET()
   
   // ���� ���� ���ε� ���� �� ���� ����
   @PostMapping("/upload")
   public void uploadPOST(MultipartFile file) { // ���۵� ���� ��ü ����
      log.info("uploadPost()");
      log.info("���� �̸� : " + file.getOriginalFilename());
      log.info("���� ũ�� : " + file.getSize());
      
      // File ��ü�� ���� ��� �� ���ϸ� ����
      File savedFile = new File(uploadPath, file.getOriginalFilename());
      try {
         file.transferTo(savedFile); // ���� ��ο� ���� ����
      } catch (Exception e) {
         log.error(e.getMessage());
      } 
   } // end uploadPOST()
   
   // ���� ���� ���ε� ���� �� ���ϵ� ����
   @PostMapping("/uploads")
   public void uploadsPost(MultipartFile[] files) { // �迭�� ���۵� ���ϵ� ����
      for(MultipartFile file : files) {
         log.info(file.getOriginalFilename());
         File savedFile = new File(uploadPath, file.getOriginalFilename());
         try {
            file.transferTo(savedFile); // ���� ��ο� ���� ����
         } catch (Exception e) {
            log.error(e.getMessage());
         } 
      }
   } // end uploadsPost()
   
} // end FileUploadController 
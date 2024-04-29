package com.soldesk.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soldesk.ex01.domain.AttachVO;
import com.soldesk.ex01.service.AttachService;
import com.soldesk.ex01.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class AttachController {

   @Autowired
   private String uploadPath;

   @Autowired
   private AttachService attachService;

   // ÷�� ���� ���ε� ������ �̵�(GET)
   @GetMapping("/register")
   public void registerGET() {
      log.info("registerGET()");
   } // end registerGET()

   // ÷�� ���� ���ε� ó��(POST)
   @PostMapping("/attach")
   public String attachPOST(AttachVO attachVO) {
      log.info("attachPost()");
      log.info("attachVO = " + attachVO);
      MultipartFile file = attachVO.getFile();

      // UUID ����
      String chgName = UUID.randomUUID().toString();
      // ���� ����
      FileUploadUtil.saveFile(uploadPath, file, chgName);

      // ���� ��� ����
      attachVO.setAttachPath(FileUploadUtil.makeDatePath());
      // ���� ���� �̸� ����
      attachVO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
      // ���� ���� �̸�(UUID) ����
      attachVO.setAttachChgName(chgName);
      // ���� Ȯ���� ����
      attachVO.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));
      // DB�� ÷�� ���� ���� ����
      log.info(attachService.createAttach(attachVO) + "�� ���");

      return "redirect:/list";
   } // end attachPOST()
   
    // ÷�� ���� ��� ��ȸ(GET)
    @GetMapping("/list")
    public void list(Model model) {
        // ÷�� ���� attachId ����Ʈ�� Model�� �߰��Ͽ� ����
        model.addAttribute("idList", attachService.getAllId());
        log.info("list()");
    }

    // ÷�� ���� �� ���� ��ȸ(GET)
    @GetMapping("/detail")
    public void detail(int attachId, Model model) {
        log.info("detail()");
        log.info("attachId : " + attachId);
        // attachId�� �� ���� ��ȸ
        AttachVO attachVO = attachService.getAttachById(attachId);
        // ��ȸ�� �� ������ Model�� �߰��Ͽ� ����
        model.addAttribute("attachVO", attachVO);
    } // end detail()
    
    // ÷�� ���� �ٿ�ε�(GET)
      // ������ Ŭ���ϸ� ����ڰ� �ٿ�ε��ϴ� ���
      // ���� ���ҽ��� �񵿱�� �����Ͽ� ���� �ٿ�ε�
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> download(int attachId) throws IOException {
        log.info("download()");
        
        // attachId�� �� ���� ��ȸ
        AttachVO attachVO = attachService.getAttachById(attachId);
        String attachPath = attachVO.getAttachPath();
        String attachChgName = attachVO.getAttachChgName();
        String attachExtension = attachVO.getAttachExtension();
        String attachRealName = attachVO.getAttachRealName();
        
        // ������ ����� ���� ���� ����
        String resourcePath = uploadPath + File.separator + attachPath 
                                    + File.separator + attachChgName;
        // ���� ���ҽ� ����
        Resource resource = new FileSystemResource(resourcePath);
        // �ٿ�ε��� ���� �̸��� ����� ����
        HttpHeaders headers = new HttpHeaders();
        String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" 
              + attachName + "." + attachExtension);

        // ������ Ŭ���̾�Ʈ�� ����
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    } // end download()


} // end FileUploadController
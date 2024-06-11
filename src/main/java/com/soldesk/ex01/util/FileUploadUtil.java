package com.soldesk.ex01.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class FileUploadUtil {
    
    /**
     * ���� �̸����� Ȯ���ڸ� ������ ���� ���� �̸��� ����
     * 
     * @param fileName ���� �̸�
     * @return ���� ���� �̸�
     */
    public static String subStrName(String fileName) {
       // FilenameUtils.normalize() : ���� �̸� ����ȭ �޼���
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
     * ���� �̸����� Ȯ���ڸ� ����
     * 
     * @param fileName ���� �̸�
     * @return Ȯ����
     */
    public static String subStrExtension(String fileName) {
        // ���� �̸����� ������ '.'�� �ε����� ã���ϴ�.
        int dotIndex = fileName.lastIndexOf('.');

        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
        String extension = fileName.substring(dotIndex + 1);

        return extension;
    }
    
    /**
     * ������ ����Ǵ� ���� �̸��� ��¥ ����(yyyy/MM/dd)���� ����
     * 
     * @return ��¥ ������ ���� �̸�
     */
    public static String makeDatePath() {
        Calendar calendar = Calendar.getInstance();
        
        String yearPath = String.valueOf(calendar.get(Calendar.YEAR));
        log.info("yearPath: " + yearPath);
        
        String monthPath = yearPath
                + File.separator
                + new DecimalFormat("00")
                    .format(calendar.get(Calendar.MONTH) + 1);
        log.info("monthPath: " + monthPath);
        
        
        String datePath = monthPath
                + File.separator
                + new DecimalFormat("00")
                    .format(calendar.get(Calendar.DATE));
        log.info("datePath: " + datePath);
        
        return datePath;
    }
    
    /**
     * ������ ����
     * 
     * @param uploadPath ���� ���ε� ���
     * @param file ���ε�� ����
     * @param uuid UUID
     */
    public static void saveFile(String uploadPath, MultipartFile file, String uuid) {
        
        File realUploadPath = new File(uploadPath, makeDatePath());
        if (!realUploadPath.exists()) {
            realUploadPath.mkdirs();
            log.info(realUploadPath.getPath() + " successfully created.");
        } else {
            log.info(realUploadPath.getPath() + " already exists.");
        }
        
        File saveFile = new File(realUploadPath, uuid);
        try {
            file.transferTo(saveFile);
            log.info("file upload scuccess");
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } 
        
    }
    
    /**
     * ������ ����
     * 
     * @param uploadPath ���� ���ε� ���
     * @param path ������ ����� ��¥ ���
     * @param chgName ����� ���� �̸�
     */
    public static void deleteFile(String uploadPath, String path, String chgName) {
        // ������ ������ ��ü ��� ����
        String fullPath = uploadPath + File.separator + path + File.separator + chgName;
        
        // ���� ��ü ����
        File file = new File(fullPath);
        
        // ������ �����ϴ��� Ȯ���ϰ� ����
        if(file.exists()) {
            if(file.delete()) {
                System.out.println(fullPath + " file delete success.");
            } else {
                System.out.println(fullPath + " file delete failed.");
            }
        } else {
            System.out.println(fullPath + " file not found.");
        }
    }
    
    /**
     * ���� ���丮�� ��� ���� �̸��� ������
     * 
     * @param uploadPath, path ���丮 ���
     * @return ���� �̸� ���
     */
    public static List<String> getFileNames(String uploadPath,String path) {
        List<String> fileNames = new ArrayList<>();
        String fullPath = uploadPath + File.separator + path + File.separator;
        File folder = new File(fullPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            } else {
                System.out.println("���� �������");
            }
        } else {
            System.out.println("���� ������");
        }

        return fileNames;
    }
}

package com.xupt.xiyoumobile.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:59
 */
@Slf4j
@Component
public class FileUploadUtil {

    @Value("${upload.diskPath}")
    private String DISK_PATH;

    public String uploadFile(String userAccount, MultipartFile multipartFile, String destPathPrefix) {

        String fileOriginName = multipartFile.getOriginalFilename();
        if (fileOriginName == null) {
            log.error("Get file original name failed! fileOriginName is null!");
            return null;
        }
        String destFilePathUri = destPathPrefix + userAccount + fileOriginName;
        String destFilePath = DISK_PATH + destFilePathUri;
        String dbPath = destFilePathUri;

        File destFile = new File(destFilePath);
        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            log.error("multipartFile transferTo a new file errored!");
            e.printStackTrace();
            return null;
        }

        return dbPath;
    }

    public boolean deleteFile(String path) {
        File delFile = new File(path);
        if (delFile.exists()) {
            if (!delFile.delete()) {
                log.error("Delete document pdf file failed!");
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}

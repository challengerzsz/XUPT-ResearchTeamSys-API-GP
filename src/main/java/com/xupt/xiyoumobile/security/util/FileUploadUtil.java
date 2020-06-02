package com.xupt.xiyoumobile.security.util;

import com.xupt.xiyoumobile.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:59
 */
@Slf4j
public class FileUploadUtil {

    public static String uploadFile(MultipartFile multipartFile, String destPathPrefix) {

        String fileOriginName = multipartFile.getOriginalFilename();
        if (fileOriginName == null) {
            log.error("Get file original name failed! fileOriginName is null!");
            return null;
        }
        String destFilePath = destPathPrefix + fileOriginName;

        File destFile = new File(destFilePath);
        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            log.error("multipartFile transferTo a new file errored!");
            e.printStackTrace();
            return null;
        }

        return destFilePath;
    }

    public static boolean deleteFile(String path) {
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

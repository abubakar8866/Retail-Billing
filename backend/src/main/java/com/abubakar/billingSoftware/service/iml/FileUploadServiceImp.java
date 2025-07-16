package com.abubakar.billingSoftware.service.iml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.abubakar.billingSoftware.service.FileUploadService;
import com.abubakar.billingSoftware.util.apputil.AppUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImp implements FileUploadService {

    @SuppressWarnings("null")
    public String uploadFile(MultipartFile file) {

        try {
            String contentType = file.getContentType();

            if (contentType.equals("image/jpg") || contentType.equals("image/jpeg") || contentType.equals("image/png")) {

                String originalFilename = file.getOriginalFilename();
                String randomString = RandomStringUtils.randomAlphanumeric(8);
                String filename = randomString + "_" + originalFilename;

                String absoluteFileLocation = AppUtil.get_photo_upload_path(filename);
                Path path = Paths.get(absoluteFileLocation);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                String imgUrl = "http://localhost:8080/uploads/" + filename;
                return imgUrl;

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only JPG, JPEG, and PNG files are allowed.");
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Boolean deleteFile(String imgUrl) {
        try {
            
            String filename = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);

            
            String absoluteFileLocation = AppUtil.get_photo_upload_path(filename);

            Path path = Paths.get(absoluteFileLocation);
            return Files.deleteIfExists(path);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete file: " + e.getMessage());
        }
    }

    
}

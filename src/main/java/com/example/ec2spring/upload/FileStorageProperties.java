package com.example.ec2spring.upload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
    private String crdTrnAplDir;
    private String prdImgDir;

//    public String getUploadDir() {
//        return uploadDir;
//    }
//
//    public void setUploadDir(String uploadDir) {
//        this.uploadDir = uploadDir;
//    }
//
//    public String getCrdTrnAplDir() {return crdTrnAplDir; }
//
//    public void setCrdTrnAplDir(String crdTrnAplDir) {
//        this.crdTrnAplDir = crdTrnAplDir;
//    }
}

package com.example.ec2spring.quillEditor;

import com.example.ec2spring.upload.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class QuillEditorService {

    private final QuillEditorMapper editorMapper;
    private final Path fileStorageLocation;

    public QuillEditorService(QuillEditorMapper editorMapper, FileStorageProperties fileStorageProperties) {
        this.editorMapper = editorMapper;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    public void putHtml(QuillEditorModel model) {
//        System.out.println(model);
        savePrdImg(model);
        editorMapper.putHtml(model);
    }

    void savePrdImg(QuillEditorModel dto) {
        String fileName;
        String prdSrcImg;
        // 이미지 저장
        if (dto.getHtmlTxt() != null) {
            // base64형식의 이미지가 있는 경우
            if (dto.getHtmlTxt().contains("base64,")) {
                prdSrcImg = dto.getHtmlTxt().split(",")[1];
                // 파일을 출력, 저장한다.
                try {
                    fileName = "test";
//                    dto.setBizrnoEvdnc(fileName);

                    Set<PosixFilePermission> perms = new HashSet<>();
                    //add owners permission 644
                    perms.add(PosixFilePermission.OWNER_READ);
                    perms.add(PosixFilePermission.OWNER_WRITE);
                    perms.add(PosixFilePermission.GROUP_READ);
                    perms.add(PosixFilePermission.OTHERS_READ);
                    Path targetLocation = this.fileStorageLocation.resolve(fileName);

                    System.out.println(targetLocation);
                    byte[] imageBytes = DatatypeConverter.parseBase64Binary(prdSrcImg);
                    BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(imageBytes));

                    float imgWidth = readImg.getWidth();
                    float imgHeight = readImg.getHeight();

                    /*이미지 리사이징 코드*/
                    if (imgWidth > 1000) {
                        int reWt = 1000;
                        int reHi = (int) (reWt*imgHeight/imgWidth) ;

                        Image reImg = readImg.getScaledInstance(reWt, reHi, Image.SCALE_SMOOTH);
                        readImg = new BufferedImage(reWt, reHi, BufferedImage.TYPE_INT_RGB);
                        Graphics graphics = readImg.getGraphics();
                        graphics.drawImage(reImg, 0, 0, null);
                        graphics.dispose();
                    }

                    ImageIO.write(readImg, "png", new File(String.valueOf(targetLocation)));

                    if (!System.getProperty("os.name").contains("Windows")) {
                        Files.setPosixFilePermissions(targetLocation, perms);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }

}

/*    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0'*/

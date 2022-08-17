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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        savePrdImg(model);
        editorMapper.putHtml(model);
    }

    void savePrdImg(QuillEditorModel dto) {
        String fileName;
        String prdSrcImg;

        // 이미지 저장
        if (dto.getPageHtml() != null) {

            Pattern pattern = Pattern.compile("(<img[^>]+src\\s*=\\s*[\\\"']?([^>\\\"']+)[\\\"']?[^>]*>)");
            Matcher matcher = pattern.matcher(dto.getPageHtml());
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                fileName = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());

                try {
                    Set<PosixFilePermission> perms = new HashSet<>();
                    //add owners permission 644
                    perms.add(PosixFilePermission.OWNER_READ);
                    perms.add(PosixFilePermission.OWNER_WRITE);
                    perms.add(PosixFilePermission.GROUP_READ);
                    perms.add(PosixFilePermission.OTHERS_READ);
                    Path targetLocation = this.fileStorageLocation.resolve(dto.getPageTarget()+"_"+fileName+".png");

                    byte[] imageBytes = DatatypeConverter.parseBase64Binary(matcher.group(2).split(",")[1]);
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
                    String fixImg = matcher.group(1).replace(matcher.group(2), "/uploads/localdir/"+dto.getPageTarget()+"_"+fileName+".png");
                    matcher.appendReplacement(buffer, fixImg);


                    ImageIO.write(readImg, "png", new File(String.valueOf(targetLocation)));


                    if (!System.getProperty("os.name").contains("Windows")) {
                        Files.setPosixFilePermissions(targetLocation, perms);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (buffer.length() > 0) {
                matcher.appendTail(buffer);
                dto.setPageHtml(buffer.toString());
            }

        }


    }

    public QuillEditorModel getHtml(String path) {
        return editorMapper.getHtml(path);
    }
}

/*    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0'*/

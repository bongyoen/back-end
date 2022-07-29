package com.example.ec2spring.info;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class InfoController {

    @GetMapping(value = "/info")
    public ResponseEntity<?> restTest() {
        System.out.println("접근완료");
        return new ResponseEntity<>("접근완료", HttpStatus.OK);
    }
}

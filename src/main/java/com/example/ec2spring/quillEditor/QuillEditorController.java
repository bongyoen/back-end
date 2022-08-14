package com.example.ec2spring.quillEditor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/quill")
public class QuillEditorController {

    private final QuillEditorService editorService;

    public QuillEditorController(QuillEditorService editorService) {
        this.editorService = editorService;
    }

    @PostMapping(value = "/saveHtml")
    public ResponseEntity<String> putHtml(@RequestBody QuillEditorModel model) {
        try {
            editorService.putHtml(model);
            return new ResponseEntity<>("저장완료", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("저장실패", HttpStatus.BAD_REQUEST);
        }
    }
}

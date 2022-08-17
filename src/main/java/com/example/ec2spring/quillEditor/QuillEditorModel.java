package com.example.ec2spring.quillEditor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuillEditorModel {
    private String htmlTxt;
    private String targetPage;
    private String title;
}

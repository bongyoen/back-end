package com.example.ec2spring.quillEditor;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuillEditorMapper {
    void putHtml(QuillEditorModel model);
}

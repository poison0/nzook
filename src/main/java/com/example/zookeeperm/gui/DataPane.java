package com.example.zookeeperm.gui;

import com.example.zookeeperm.gui.editor.TextEditor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

public class DataPane extends JBPanel<DataPane> {
    private final TextEditor textEditor;
    public DataPane(Project project,String text) {
        super();
        setLayout(new BorderLayout());
        textEditor = new TextEditor(project);
        textEditor.setText(text);
        add(textEditor, BorderLayout.CENTER);
        JPanel bodyFileTypePanel = new JPanel(new BorderLayout());
        ComboBox<FileType> requestBodyFileType = new ComboBox<>(new FileType[]{
                TextEditor.TEXT_FILE_TYPE,
                TextEditor.JSON_FILE_TYPE,
                TextEditor.HTML_FILE_TYPE,
                TextEditor.XML_FILE_TYPE
        });
        requestBodyFileType.setRenderer(new FileTypeRenderer());
        requestBodyFileType.setFocusable(false);
        bodyFileTypePanel.add(requestBodyFileType, BorderLayout.CENTER);
        add(bodyFileTypePanel, BorderLayout.SOUTH);
    }

    public void setText(String text) {
        this.textEditor.setText(text);
    }
}

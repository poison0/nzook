package com.azure.nzook.gui;

import com.azure.nzook.gui.editor.TextEditor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DataPane extends JBPanel<DataPane> {
    private final TextEditor textEditor;
    public DataPane(Project project,String text) {
        super();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(JBUI.emptyInsets()));
        textEditor = new TextEditor(project,TextEditor.TEXT_FILE_TYPE);
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

        requestBodyFileType.addItemListener(e -> {
            Object selectedObject = e.getItemSelectable().getSelectedObjects()[0];
            if (selectedObject instanceof FileType) {
                FileType fileType = (FileType) selectedObject;
                textEditor.setFileType(fileType);
            }
        });

        add(bodyFileTypePanel, BorderLayout.SOUTH);
    }

    public void setText(String text) {
        this.textEditor.setText(text);
    }
}

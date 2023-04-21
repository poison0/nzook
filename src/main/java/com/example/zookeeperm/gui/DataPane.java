package com.example.zookeeperm.gui;

import com.example.zookeeperm.gui.editor.TextEditor;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;

public class DataPane extends JBPanel<DataPane> {
    private JBLabel headerLabel = new JBLabel();
    private JBPanel<HttpView> descriptionPanel = new JBPanel<>(new BorderLayout());

    public DataPane(Project project) {
        super();
        setLayout(new BorderLayout());
        headerLabel.setIcon(AllIcons.General.BalloonInformation);
        headerLabel.setFont(UIUtil.getLabelFont().deriveFont((UIUtil.getLabelFont().getSize2D() + JBUIScale.scale(2))).deriveFont(Font.BOLD));
        headerLabel.setBorder(JBUI.Borders.empty(5, 10));
        ////--------
        headerLabel.setText("/zookeepe");
        ////--------
        add(headerLabel, BorderLayout.NORTH);
        descriptionPanel.add(new TextEditor(project), BorderLayout.CENTER);

        add(descriptionPanel, BorderLayout.CENTER);

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
        add(bodyFileTypePanel, BorderLayout.NORTH);

    }
}

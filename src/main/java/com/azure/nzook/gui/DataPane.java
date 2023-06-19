package com.azure.nzook.gui;

import com.azure.nzook.gui.editor.TextEditor;
import com.azure.nzook.util.Bundle;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 * @author niu
 * @since 1.0
 */
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
        bodyFileTypePanel.add(toolsBarPanel(), BorderLayout.WEST);
        add(bodyFileTypePanel, BorderLayout.NORTH);
    }
    /**
     * @since 1.0.2
     */
    private JPanel toolsBarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(JBUI.Borders.empty(2, 0));
        ComboBox<FileType> requestBodyFileType = new ComboBox<>(new FileType[]{
                TextEditor.TEXT_FILE_TYPE,
                TextEditor.JSON_FILE_TYPE,
                TextEditor.HTML_FILE_TYPE,
                TextEditor.XML_FILE_TYPE
        });
        requestBodyFileType.setRenderer(new FileTypeRenderer());
        requestBodyFileType.setFocusable(false);
        requestBodyFileType.addItemListener(e -> {
            Object selectedObject = e.getItemSelectable().getSelectedObjects()[0];
            if (selectedObject instanceof FileType fileType) {
                textEditor.setFileType(fileType);
            }
        });
        panel.add(requestBodyFileType, BorderLayout.CENTER);
        panel.add(new JBLabel(Bundle.getString("panel.data.comboBox.label")), BorderLayout.WEST);
        panel.setBorder(JBUI.Borders.empty(2, 15));
        return panel;
    }

    public void setText(String text) {
        this.textEditor.setText(text);
    }
}

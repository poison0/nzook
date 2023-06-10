package com.example.zookeeperm.gui;

import com.example.zookeeperm.action.datapane.FileTypeAction;
import com.example.zookeeperm.gui.editor.TextEditor;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.editor.actions.ScrollToTheEndToolbarAction;
import com.intellij.openapi.editor.actions.ToggleUseSoftWrapsMenuAction;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Supplier;

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

    private ActionToolbarImpl createToolbar() {
        DefaultActionGroup presentationGroup = new DefaultActionGroup();
        presentationGroup.setPopup(true);
        presentationGroup.getTemplatePresentation().setIcon(AllIcons.Actions.MoreHorizontal);
        presentationGroup.add(new FileTypeAction(textEditor, TextEditor.TEXT_FILE_TYPE,"text"));
        presentationGroup.add(new FileTypeAction(textEditor, TextEditor.JSON_FILE_TYPE,"json"));
        presentationGroup.add(new FileTypeAction(textEditor, TextEditor.HTML_FILE_TYPE,"html"));
        presentationGroup.add(new FileTypeAction(textEditor, TextEditor.XML_FILE_TYPE,"xml"));
        DefaultActionGroup defaultActionGroup = new DefaultActionGroup(presentationGroup);
        ActionToolbarImpl actionToolbar = new ActionToolbarImpl("data pane",defaultActionGroup,true);
        actionToolbar.setTargetComponent(this);
        return actionToolbar;
    }

    public void setText(String text) {
        this.textEditor.setText(text);
    }
}

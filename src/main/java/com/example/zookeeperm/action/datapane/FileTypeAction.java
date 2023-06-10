package com.example.zookeeperm.action.datapane;

import com.example.zookeeperm.gui.editor.TextEditor;
import com.github.hypfvieh.util.StringUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * @auth nss
 */
public class FileTypeAction extends AnAction {

    private final TextEditor textEditor;
    private final FileType fileType;


    public FileTypeAction(TextEditor textEditor, FileType fileType,String name) {
        super(name, null, fileType.getIcon());
        this.textEditor = textEditor;
        this.fileType = fileType;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        textEditor.setFileType(fileType);
    }
}
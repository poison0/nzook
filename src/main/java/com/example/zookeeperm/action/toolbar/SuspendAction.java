package com.example.zookeeperm.action.toolbar;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

public class SuspendAction extends AbstractAction {

    public SuspendAction() {
        super("Stop", "Stop", AllIcons.Actions.Suspend);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

    // 该方法用于设置Action的可用性
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return true;
    }
}

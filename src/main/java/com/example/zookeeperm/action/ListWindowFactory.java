package com.example.zookeeperm.action;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class ListWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory instance = ContentFactory.SERVICE.getInstance();
        SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false,true);
        Content content = instance.createContent(panel, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}

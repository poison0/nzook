package com.azure.nzook.action;

import com.azure.nzook.action.toolbar.*;
import com.azure.nzook.gui.OperationWindow;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author niu
 * @since 1.0
 */
public class ListWindowFactory implements ToolWindowFactory {

    public static OperationWindow operationWindow;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ContentFactory instance = ContentFactory.getInstance();
        SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false,true);
        Content content = instance.createContent(panel, "", false);
        operationWindow = new OperationWindow(project,toolWindow);
        JPanel contentPanel = operationWindow.getContentPanel(panel);
        panel.setContent(contentPanel);
        panel.setToolbar(createToolBar(panel).getComponent());
        toolWindow.getContentManager().addContent(content);
    }

    private ActionToolbar createToolBar(JBPanel<?> jbPanel) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new ExecuteAction());
        group.add(new SuspendAction());
        group.add(new ReloadAction());
        group.addSeparator();
        group.add(new ExpandAllAction());
        group.add(new CollapseAllAction());
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("nzookToolsBar", group, false);
        toolbar.setOrientation(SwingConstants.VERTICAL);
        toolbar.setTargetComponent(jbPanel);
        return toolbar;
    }

}

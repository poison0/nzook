package com.example.zookeeperm.action;

import com.example.zookeeperm.action.toolbar.RefreshTabAction;
import com.example.zookeeperm.action.toolbar.SignInAction;
import com.example.zookeeperm.action.toolbar.SignOutAction;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.service.Login;
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
import org.apache.zookeeper.KeeperException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

public class ListWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ContentFactory instance = ContentFactory.SERVICE.getInstance();
        SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false,true);
        Content content = instance.createContent(panel, "", false);
        OperationWindow operationWindow = new OperationWindow(toolWindow, getNodeData());
        JPanel contentPanel = operationWindow.getContentPanel();
        panel.setContent(contentPanel);
        panel.setToolbar(createToolBar(panel).getComponent());
        toolWindow.getContentManager().addContent(content);
    }

    private ActionToolbar createToolBar(JBPanel<?> jbPanel) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new SignInAction());
        group.add(new RefreshTabAction());
        group.add(new SignOutAction());
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("zookeeperMToolsBar", group, false);
        toolbar.setOrientation(SwingConstants.VERTICAL);
        toolbar.setTargetComponent(jbPanel);
        return toolbar;
    }

    private NodeData getNodeData() {
        //todo
        try {
            return Login.login();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

}

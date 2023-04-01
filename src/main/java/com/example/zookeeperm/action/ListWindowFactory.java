package com.example.zookeeperm.action;

import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.service.Login;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.dsl.gridLayout.GridLayout;
import com.intellij.uiDesigner.core.GridLayoutManager;
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
        toolWindow.getContentManager().addContent(content);
    }

    private NodeData getNodeData() {
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

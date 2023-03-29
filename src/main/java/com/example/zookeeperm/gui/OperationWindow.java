package com.example.zookeeperm.gui;

import com.intellij.ide.ui.laf.darcula.ui.DarculaSplitPaneUI;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;

public class OperationWindow {
    private JPanel contentPanel;
    private JSplitPane splitPane;
    private JScrollPane leftPane;
    private JPanel rightPane;
    private JTree fieldTree;
    public void init(ToolWindow toolWindow) {
        contentPanel = new JBPanel<>();
        leftPane = new JBScrollPane();
        rightPane = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        splitPane.setUI(new DarculaSplitPaneUI());
        contentPanel.add(splitPane);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1");
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2");
        root.add(child1);
        root.add(child2);
        fieldTree = new JTree(root);
        leftPane.setViewportView(fieldTree);
    }

    public OperationWindow(ToolWindow toolWindow) {
        init(toolWindow);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}

package com.example.zookeeperm.gui;

import javax.swing.*;
import com.intellij.openapi.wm.ToolWindow;

public class OperationWindow {
    private JPanel contentPanel;
    private JSplitPane splitPane;
    private JScrollPane leftPane;
    private JPanel rightPane;
    private JTree fieldTree;

    public void init(ToolWindow toolWindow) {
        fieldTree.setCellRenderer(new TreeCell());
    }
}

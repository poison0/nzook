package com.example.zookeeperm.gui;

import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.intellij.ide.ui.laf.darcula.ui.DarculaSplitPaneUI;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.*;
import com.intellij.ui.components.JBComboBoxLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import com.intellij.xdebugger.impl.ui.TextViewer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class OperationWindow {

    private static final String RIGHT_SPLITTER_KEY = "sonarlint_rule_configuration_splitter_right";
    private static final float DIVIDER_PROPORTION_RULE_DEFAULT = 0.85f;
    private JScrollPane leftPane;
    private JBSplitter splitter;
    private JPanel rightPane;
    public void init(ToolWindow toolWindow,NodeData nodeData) {
        leftPane = new JBScrollPane();
        rightPane = new JPanel(new BorderLayout());
        splitter = new OnePixelSplitter(false, 0.85f, 0.01f, 0.99f);
        splitter.setHonorComponentsMinimumSize(true);
        splitter.setSplitterProportionKey("MAIN_SPLITTER_KEY");
        splitter.setFirstComponent(leftPane);
        splitter.setSecondComponent(rightPane);
        leftPane.setViewportView(createTree(nodeData));
        leftPane.setColumnHeaderView(new JLabel(LoginData.ip + ":" + LoginData.port));
        JBComboBoxLabel jbComboBoxLabel = new JBComboBoxLabel();
        jbComboBoxLabel.setText("test");
        rightPane.add(jbComboBoxLabel,BorderLayout.CENTER);
    }

    private JTree createTree(NodeData nodeData) {

        if (nodeData == null) {
            return new SimpleTree();
        }

        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(getTreeNode(nodeData));
        SimpleTree fieldTree = new SimpleTree(defaultTreeModel);
        fieldTree.setCellRenderer(new TreeCell());
        return fieldTree;
    }

    private DefaultMutableTreeNode getTreeNode(NodeData nodeData) {
        DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(nodeData);
        if (nodeData.getChildrenList() == null || nodeData.getChildrenList().isEmpty()) {
            return defaultMutableTreeNode;
        }
        for (NodeData child : nodeData.getChildrenList()) {
            defaultMutableTreeNode.add(getTreeNode(child));
        }
        return defaultMutableTreeNode;
    }


    public OperationWindow(ToolWindow toolWindow, NodeData nodeData) {
        init(toolWindow,nodeData);
    }

    public JPanel getContentPanel() {
        return splitter;
    }
}

package com.example.zookeeperm.gui;

import com.example.zookeeperm.action.rightclick.*;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBComboBoxLabel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
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

    /**
     * Create tree.
     */
    private PathTree createTree(NodeData nodeData) {

        if (nodeData == null) {
            return new PathTree();
        }

        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(getTreeNode(nodeData));
        PathTree fieldTree = new PathTree(defaultTreeModel);
        fieldTree.setCellRenderer(new TreeCell());
        installPopupMenu(fieldTree);
        return fieldTree;
    }
    /**
     *  安装右键菜单
     */
    private void installPopupMenu(PathTree tree) {
        tree.setShowsRootHandles(true);
        tree.expandRow(0);

        var group = new DefaultActionGroup();
        group.add(new AddAction());
        group.addSeparator();
        group.add(new CopyPathAction());
        group.add(new CopyPathFromRootAction());
        group.addSeparator();
        group.add(new ExpandAllAction());
        group.add(new CollapseAllAction());
        group.addSeparator();
        group.add(new DeleteAction());
        group.addSeparator();
        PopupHandler.installPopupMenu(tree, group, ActionPlaces.TODO_VIEW_POPUP);
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

package com.example.zookeeperm.gui;

import com.example.zookeeperm.action.menu.*;
import com.example.zookeeperm.action.toolbar.CollapseAllAction;
import com.example.zookeeperm.action.toolbar.ExpandAllAction;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class OperationWindow {

    private JScrollPane leftPane;
    private JBSplitter splitter;
    public void init(ToolWindow toolWindow,NodeData nodeData) {
        leftPane = new JBScrollPane();
        splitter = new OnePixelSplitter(true, 0.85f, 0.01f, 0.99f);
        splitter.setHonorComponentsMinimumSize(true);
        splitter.setSplitterProportionKey("MAIN_SPLITTER_KEY");
        splitter.setFirstComponent(leftPane);

        JBTabbedPane detailsTab = new JBTabbedPane();
        detailsTab.insertTab("Data", null, new DataPane(), "Node data", 0);
        detailsTab.insertTab("Metadata", null, new JBPanel<>(), "Stat data", 1);
        detailsTab.insertTab("ACL", null, new JBPanel<>(), "Access control list", 2);
        splitter.setSecondComponent(detailsTab);


        leftPane.setViewportView(createTree(nodeData));
        leftPane.setColumnHeaderView(new JLabel(LoginData.ip + ":" + LoginData.port));
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
        group.add(new EditAction());
        group.addSeparator();
        group.add(new CopyPathAction());
        group.add(new CopyPathFromRootAction());
        group.addSeparator();
        group.add(new DeleteAction());
        group.addSeparator();
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

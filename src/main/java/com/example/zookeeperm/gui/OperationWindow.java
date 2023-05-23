package com.example.zookeeperm.gui;

import com.example.zookeeperm.action.menu.*;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.renderer.TreeCell;
import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import org.apache.zookeeper.KeeperException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.example.zookeeperm.data.LoginData.zooKeeper;
import static com.example.zookeeperm.data.LoginData.zookeeperOperationService;

public class OperationWindow {

    private JScrollPane leftPane;
    private static Project project;
    private JBSplitter splitter;

    private JBTabbedPane detailsTab;

    public void init(ToolWindow toolWindow,NodeData nodeData) {
        leftPane = new JBScrollPane();
        splitter = new OnePixelSplitter(true, 0.85f, 0.01f, 0.99f);
        splitter.setHonorComponentsMinimumSize(true);
        splitter.setSplitterProportionKey("MAIN_SPLITTER_KEY");
        splitter.setFirstComponent(leftPane);
        detailsTab = new JBTabbedPane(nodeData, project);
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
        addMouseClicked(fieldTree);
        fieldTree.setCellRenderer(new TreeCell());
        installPopupMenu(fieldTree);
        return fieldTree;
    }

    /**
     * 添加鼠标点击事件
     */
    private void addMouseClicked(PathTree fieldTree) {
        fieldTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) fieldTree.getLastSelectedPathComponent();
                if(node == null){
                    return;
                }
                NodeData userObject = (NodeData)node.getUserObject();
                try {
                    switchNode(userObject);
                } catch (InterruptedException ex) {
                    //todo
                    throw new RuntimeException(ex);
                } catch (KeeperException ex) {
                    //todo
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void switchNode(NodeData nodeData) throws InterruptedException, KeeperException {
        zookeeperOperationService.setAcl(nodeData,zooKeeper);
        detailsTab.setNodeData(nodeData);
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
        group.add(new RefreshAction());
        group.addSeparator();
        group.add(new CopyPathAction());
        group.add(new CopyPathFromRootAction());
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


    public OperationWindow(@NotNull Project project,ToolWindow toolWindow, NodeData nodeData) {
        OperationWindow.project = project;
        init(toolWindow,nodeData);
    }

    public JPanel getContentPanel() {
        return splitter;
    }
}

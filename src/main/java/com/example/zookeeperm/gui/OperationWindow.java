package com.example.zookeeperm.gui;

import com.example.zookeeperm.action.menu.*;
import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.renderer.TreeCell;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBScrollPane;
import org.apache.zookeeper.KeeperException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.example.zookeeperm.data.LoginData.zooKeeper;
import static com.example.zookeeperm.data.LoginData.zookeeperOperationService;

public class OperationWindow {
    public static PathTree tree;
    public ToolWindow toolWindow;

    private JScrollPane leftPane;
    private static Project project;
    public JPanel mainPanel = new JPanel(new BorderLayout());
    private JBSplitter splitter;
    private JComponent parentPanel;

    private JBTabbedPane detailsTab;

    public void init(NodeData nodeData) {
        leftPane = new JBScrollPane();
        tree = createTree(nodeData);
        leftPane.setViewportView(tree);
        leftPane.setColumnHeaderView(new JLabel(LoginData.ip + ":" + LoginData.port));
        splitter = getSplitter(toolWindow);
        splitter.setHonorComponentsMinimumSize(true);
        splitter.setSplitterProportionKey("MAIN_SPLITTER_KEY");
        splitter.setFirstComponent(leftPane);
        detailsTab = new JBTabbedPane(nodeData, project);
        splitter.setSecondComponent(detailsTab);
        mainPanel.add(splitter, BorderLayout.CENTER);
    }
    public OnePixelSplitter getSplitter(ToolWindow toolWindow) {
        OnePixelSplitter onePixelSplitter = new OnePixelSplitter(splitVertically(project), 0.85f, 0.01f, 0.99f);
        final var listener = new ToolWindowManagerListener() {
            @Override
            public void stateChanged(@NotNull ToolWindowManager toolWindowManager) {
                onePixelSplitter.setOrientation(splitVertically(project));
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        };
        project.getMessageBus().connect(toolWindow.getContentManager()).subscribe(ToolWindowManagerListener.TOPIC, listener);
        Disposer.register(toolWindow.getContentManager(), () -> {
            parentPanel.remove(splitter);
            splitter.dispose();
        });
        return onePixelSplitter;
    }


    public static boolean splitVertically(Project project) {
        ToolWindowManager instance = ToolWindowManager.getInstance(project);
        final var toolWindow = instance.getToolWindow(Constant.TOOL_WINDOW_ID);
        var splitVertically = false;
        if (toolWindow != null) {
            final var anchor = toolWindow.getAnchor();
            splitVertically = anchor == ToolWindowAnchor.LEFT || anchor == ToolWindowAnchor.RIGHT;
        }
        return splitVertically;
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


    public OperationWindow(@NotNull Project project,ToolWindow toolWindow) {
        OperationWindow.project = project;
        this.toolWindow = toolWindow;
    }

    public JPanel getContentPanel(JComponent parentPanel) {
        this.parentPanel = parentPanel;
        return mainPanel;
    }
}

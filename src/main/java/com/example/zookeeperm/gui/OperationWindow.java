package com.example.zookeeperm.gui;

import com.example.zookeeperm.action.menu.*;
import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.renderer.TreeCell;
import com.example.zookeeperm.message.Notifier;
import com.example.zookeeperm.service.Login;
import com.example.zookeeperm.util.Bundle;
import com.example.zookeeperm.util.DataUtils;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
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
    private static PathTree tree;
    private final ToolWindow toolWindow;

    private static Project project;
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private JBSplitter splitter;
    private JComponent parentPanel;

    private MyJBTabbedPane detailsTab;

    public void init(NodeData nodeData) {
        JScrollPane leftPane = new JBScrollPane();
        setTree(createTree(nodeData));
        leftPane.setViewportView(tree);
        leftPane.setColumnHeaderView(new JLabel(DataUtils.getCurrentLoginData().getIp()+" "+DataUtils.getCurrentLoginData().getPort()));
        splitter = getSplitter(toolWindow);
        splitter.setHonorComponentsMinimumSize(true);
        splitter.setSplitterProportionKey("MAIN_SPLITTER_KEY");
        splitter.setFirstComponent(leftPane);
        detailsTab = new MyJBTabbedPane(nodeData, project);
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

    public static PathTree getTree() {
        return tree;
    }
    public static void setTree(PathTree tree) {
        OperationWindow.tree = tree;
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
                } catch (KeeperException ex) {
                    Notifier.notify(ex.getMessage(), MessageType.ERROR);
                } catch (InterruptedException ex) {
                    Notifier.notify(ex.getMessage(), MessageType.ERROR);
                    Thread.currentThread().interrupt();
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

    public static DefaultMutableTreeNode getTreeNode(NodeData nodeData) {
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
        setDefaultPanel();
        if (Boolean.TRUE.equals(DataUtils.isLogin())) {
            Login.load(project, DataUtils.getCurrentLoginData());
        }
    }

    public JPanel getContentPanel(JComponent parentPanel) {
        this.parentPanel = parentPanel;
        return mainPanel;
    }
    public void setDefaultPanel() {
        EmptyTextPanel emptyText = new EmptyTextPanel();
        emptyText.withEmptyText(Bundle.getString("panel.empty.description")+" ",Bundle.getString("panel.empty.link"), e -> Login.popupLoginDialog(project));
        mainPanel.add(emptyText, BorderLayout.CENTER);
    }

    public void clearAll() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}

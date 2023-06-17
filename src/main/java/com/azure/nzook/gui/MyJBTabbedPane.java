package com.azure.nzook.gui;

import com.azure.nzook.data.NodeData;
import com.azure.nzook.util.Bundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.impl.content.ToolWindowContentUi;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.awt.*;

import static com.intellij.ui.components.JBTabbedPane.LABEL_FROM_TABBED_PANE;

/**
 * @author niu
 * @version 1.0
 */
public class MyJBTabbedPane extends JTabbedPane {

    private final DataPane dataPane;
    private final ListViewPane metaPane;
    private final ListViewPane aclPane;

    public MyJBTabbedPane(NodeData nodeData, Project project) {
        dataPane = new DataPane(project, nodeData.getData());
        metaPane = new ListViewPane(nodeData.getMetaData().getViewData());
        aclPane = new ListViewPane(nodeData.getAclItemList());
        insertTab(Bundle.getString("table.header.nodeData"), null, dataPane, Bundle.getString("table.header.nodeData.description"),0);
        insertTab(Bundle.getString("table.header.statData"), null,metaPane , Bundle.getString("table.header.statData.description"),1);
        insertTab(Bundle.getString("table.header.acl"), null, aclPane, Bundle.getString("table.header.acl.description"), 2);
    }

    @Override
    public void insertTab(@Nls(capitalization = Nls.Capitalization.Title) String title, Icon icon, Component component,
                          @Nls(capitalization = Nls.Capitalization.Sentence) String tip, int index) {
        super.insertTab(title, icon, component, tip, index);

        JLabel label = new JLabel(title);
        label.setIcon(icon);
        label.setBorder(JBUI.Borders.empty());
        label.setFont(getFont());
        setTabComponentAt(index, label);
        label.putClientProperty(LABEL_FROM_TABBED_PANE, Boolean.TRUE);
        setInsets(component);
        revalidate();
        repaint();
    }

    private void setInsets(Component component) {
        if (component instanceof JComponent) {
            UIUtil.addInsets((JComponent)component, JBUI.emptyInsets());
        }
    }

    public void setNodeData(NodeData nodeData) {
        dataPane.setText(nodeData.getData());
        metaPane.setList(nodeData.getMetaData().getViewData());
        aclPane.setList(nodeData.getAclItemList());
    }
}

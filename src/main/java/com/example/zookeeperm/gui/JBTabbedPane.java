package com.example.zookeeperm.gui;

import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.awt.*;

import static com.intellij.ui.components.JBTabbedPane.LABEL_FROM_TABBED_PANE;

/**
 * @author niu
 */
public class JBTabbedPane extends JTabbedPane {

    private final Insets myTabComponentInsets = UIUtil.PANEL_SMALL_INSETS;
    private final DataPane dataPane;
    private final ListViewPane metaPane;
    private final ListViewPane aclPane;

    public JBTabbedPane(NodeData nodeData, Project project) {
        dataPane = new DataPane(project, nodeData.getData());
        metaPane = new ListViewPane(nodeData.getMetaData().getViewData());
        aclPane = new ListViewPane(nodeData.getAclList());
        insertTab(Bundle.getString("table.header.nodeData"), null, dataPane, Bundle.getString("table.header.nodeData.description"),0);
        insertTab(Bundle.getString("table.header.statData"), null,metaPane , Bundle.getString("table.header.statData.description"),1);
        insertTab(Bundle.getString("table.header.acl"), null, aclPane, Bundle.getString("table.header.acl.description"), 2);
    }

    @Override
    public void insertTab(@Nls(capitalization = Nls.Capitalization.Title) String title, Icon icon, Component component,
                          @Nls(capitalization = Nls.Capitalization.Sentence) String tip, int index) {
        super.insertTab(title, icon, component, tip, index);

        //set custom label for correct work spotlighting in settings
        JLabel label = new JLabel(title);
        label.setIcon(icon);
        label.setBorder(JBUI.Borders.empty(1));
        label.setFont(getFont());
        setTabComponentAt(index, label);
        label.putClientProperty(LABEL_FROM_TABBED_PANE, Boolean.TRUE);
        setInsets(component);
        revalidate();
        repaint();
    }

    private void setInsets(Component component) {
        if (component instanceof JComponent && myTabComponentInsets != null) {
            UIUtil.addInsets((JComponent)component, myTabComponentInsets);
        }
    }

    public void setNodeData(NodeData nodeData) {
        dataPane.setText(nodeData.getData());
        metaPane.setList(nodeData.getMetaData().getViewData());
        aclPane.setList(nodeData.getAclList());
    }
}

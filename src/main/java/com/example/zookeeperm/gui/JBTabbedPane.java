package com.example.zookeeperm.gui;

import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import static com.intellij.ui.components.JBTabbedPane.LABEL_FROM_TABBED_PANE;

public class JBTabbedPane extends JTabbedPane implements HierarchyListener {

    private Insets myTabComponentInsets = UIUtil.PANEL_SMALL_INSETS;

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        //todo
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

        component.addHierarchyListener(this);
        setInsets(component);

        revalidate();
        repaint();
    }

    private void setInsets(Component component) {
        if (component instanceof JComponent && myTabComponentInsets != null) {
            UIUtil.addInsets((JComponent)component, myTabComponentInsets);
        }
    }
}

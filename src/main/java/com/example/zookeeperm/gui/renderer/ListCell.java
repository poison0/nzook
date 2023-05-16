package com.example.zookeeperm.gui.renderer;

import com.example.zookeeperm.data.ListItem;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author nss
 */
public class ListCell extends JBPanel<ListCell> implements ListCellRenderer<ListItem> {
    @Override
    public Component getListCellRendererComponent(JList<? extends ListItem> list, ListItem value, int index, boolean isSelected, boolean cellHasFocus)
    {
        if (isSelected) {

        }
        return this;
    }
}

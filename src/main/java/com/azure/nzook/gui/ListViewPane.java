package com.azure.nzook.gui;

import com.azure.nzook.data.ListItem;
import com.azure.nzook.gui.renderer.ListCell;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.util.List;
/**
 * @author niu
 * @version 1.0
 */
public class ListViewPane extends JBScrollPane {
    DefaultListModel<ListItem> tableModel;
    public ListViewPane(List<ListItem> listItems) {
        super();
        tableModel = new DefaultListModel<>();
        tableModel.addAll(listItems);
        JBList<ListItem> table = new JBList<>(tableModel);
        table.setCellRenderer(new ListCell());
        table.setModel(tableModel);
        setViewportView(table);
    }

    public void setList(List<ListItem> listItems) {
        tableModel.clear();
        tableModel.addAll(listItems);
    }

}

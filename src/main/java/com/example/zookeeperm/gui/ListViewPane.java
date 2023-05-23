package com.example.zookeeperm.gui;

import com.example.zookeeperm.data.ListItem;
import com.example.zookeeperm.gui.renderer.ListCell;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.util.List;

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

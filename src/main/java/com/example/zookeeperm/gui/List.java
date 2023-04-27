package com.example.zookeeperm.gui;


import com.example.zookeeperm.data.ListItem;
import com.intellij.ui.components.JBList;

import javax.swing.*;

/**
 * @author nss
 */
public class List extends JBList<ListItem> {

    public List(DefaultListModel<ListItem> listModel) {
        super();
        setModel(listModel);
        setFixedCellHeight(20);

    }
}

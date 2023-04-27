package com.example.zookeeperm.gui.renderer;

import com.example.zookeeperm.data.ListItem;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author nss
 * @description
 * @date 2023/4/27
 * @copyright 广州瑞云网络科技有限公司
 */
public class ListCell extends JBPanel<ListCell> implements ListCellRenderer<ListItem> {
    @Override
    public Component getListCellRendererComponent(JList<? extends ListItem> list, ListItem value, int index, boolean isSelected, boolean cellHasFocus) {
        return this;
    }
}

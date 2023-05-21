package com.example.zookeeperm.gui.renderer;

import com.example.zookeeperm.data.ListItem;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author nss
 */
public class ListCell extends ColoredListCellRenderer<ListItem> {
    @Override
    protected void customizeCellRenderer(@NotNull JList<? extends ListItem> list, ListItem value, int index, boolean selected, boolean hasFocus) {
        if (value.getBlack()) {
            return;
        }
        append(value.getKey()+":  ", SimpleTextAttributes.GRAYED_ATTRIBUTES,true);
        append(value.getValue(), SimpleTextAttributes.REGULAR_ATTRIBUTES, true);
        appendTextPadding(30);
    }
}

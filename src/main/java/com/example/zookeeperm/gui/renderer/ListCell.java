package com.example.zookeeperm.gui.renderer;

import com.example.zookeeperm.data.ListItem;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
        if (("Last Modified Time".equals(value.getKey()) || "Creation Time".equals(value.getKey()))
                && value.getValue() != null && !"0".equals(value.getValue())) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(value.getValue()));
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            append(" ["+localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+"]", SimpleTextAttributes.GRAY_ATTRIBUTES);
        }
        appendTextPadding(30);
    }
}

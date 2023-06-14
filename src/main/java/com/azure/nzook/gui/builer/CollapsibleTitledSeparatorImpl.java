package com.azure.nzook.gui.builer;

import com.intellij.openapi.observable.properties.AtomicBooleanProperty;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.TitledSeparator;
import com.intellij.util.ui.IndentedIcon;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author niu
 * @version 1.0.1
 */
public class CollapsibleTitledSeparatorImpl extends TitledSeparator {

    boolean expanded;

    private AtomicBooleanProperty expandedProperty = new AtomicBooleanProperty(true);

    public CollapsibleTitledSeparatorImpl(String title) {
        super(title);
        updateIcon();
        expanded = expandedProperty.get();
        expandedProperty.afterChange(var -> {
            CollapsibleTitledSeparatorImpl.this.updateIcon();
            return null;
        });
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        setCursor(cursor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                expanded = !expanded;
                expandedProperty.set(expanded);
            }
        });
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        expandedProperty.set(expanded);
    }
    public void onAction(Function1<Boolean, Unit> action) {
        expandedProperty.afterChange(action);
    }

    private void updateIcon() {
        Icon treeExpandedIcon = UIUtil.getTreeExpandedIcon();
        Icon treeCollapsedIcon = UIUtil.getTreeCollapsedIcon();
        int width = Math.max(treeExpandedIcon.getIconWidth(), treeCollapsedIcon.getIconWidth());
        Icon icon;
        if (expanded) {
            icon = treeExpandedIcon;
        }else {
            icon = treeCollapsedIcon;
        }
        int extraSpace = width - icon.getIconWidth();

        if (extraSpace > 0) {
            int left = extraSpace / 2;
            icon = new IndentedIcon(icon, JBUI.insets(0, left, 0, extraSpace - left));
        }
        getLabel().setIcon(icon);
        getLabel().setDisabledIcon(IconLoader.getTransparentIcon(icon, 0.5f));
    }
}

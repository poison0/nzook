package com.azure.nzook.gui;

import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBPanelWithEmptyText;
import com.intellij.util.ui.ComponentWithEmptyText;
import com.intellij.util.ui.JBSwingUtilities;
import com.intellij.util.ui.StatusText;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * @author niu
 * @since 1.0
 */
public class EmptyTextPanel extends JBPanel<JBPanelWithEmptyText> implements ComponentWithEmptyText {
    private final StatusText emptyText = new StatusText(this) {
        @Override
        protected boolean isStatusVisible() {
            return Arrays.stream(getComponents()).noneMatch(Component::isVisible);
        }
    };

    public EmptyTextPanel() {
        super();
    }

    public EmptyTextPanel(LayoutManager layout) {
        super(layout);
    }

    @Override
    public @NotNull StatusText getEmptyText() {
        return emptyText;
    }

    public EmptyTextPanel withEmptyText(String str, String linkText, ActionListener listener) {
        emptyText.setText(str);
        emptyText.appendText(linkText, SimpleTextAttributes.LINK_PLAIN_ATTRIBUTES,listener);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        emptyText.paint(this, g);
    }

    @Override
    protected Graphics getComponentGraphics(Graphics graphics) {
        return JBSwingUtilities.runGlobalCGTransform(this, super.getComponentGraphics(graphics));
    }

}

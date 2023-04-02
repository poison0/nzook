package com.example.zookeeperm.gui;

import com.intellij.ui.BrowserHyperlinkListener;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.HTMLEditorKitBuilder;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.SwingHelper;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class HttpView extends JBPanel<HttpView>{

    private final JEditorPane editor = getEditor();

    public HttpView() {
        super();
        JScrollPane scrollableRulePanel = ScrollPaneFactory.createScrollPane(editor, true);
        scrollableRulePanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableRulePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableRulePanel.getVerticalScrollBar().setUnitIncrement(10);
        scrollableRulePanel.setOpaque(false);
        scrollableRulePanel.getViewport().setOpaque(false);
        add(scrollableRulePanel, BorderLayout.CENTER);
    }

    private JEditorPane getEditor() {
        JEditorPane editor = new JEditorPane();
        editor.setContentType(UIUtil.HTML_MIME);
        ((DefaultCaret)editor.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        editor.setEditorKit(HTMLEditorKitBuilder.simple());
        editor.setBorder(JBUI.Borders.empty(10));
        editor.setEditable(false);
        editor.setOpaque(false);
        editor.addHyperlinkListener(BrowserHyperlinkListener.INSTANCE);
        return editor;
    }

    public void updateHtml(String html) {
        SwingHelper.setHtml(editor, html, UIUtil.getLabelForeground());
    }
}

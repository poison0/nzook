package com.example.zookeeperm.gui.pop;

import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

/**
 * @auth nss
 */
public class LoginDialog extends AbstractDialog {
    /**
     * 对话框大小
     */
    private static final Integer WIDTH = 230;
    /**
     * 对话框大小
     */
    private static final Integer HEIGHT = 60;

    public LoginDialog(String title) {
        super(title);
    }

    @Override
    protected Integer getPanelWidth() {
        return WIDTH;
    }

    @Override
    protected Integer getPanelHeight() {
        return HEIGHT;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel panel = new JPanel(gridBagLayout);
        panel.setBorder(JBUI.Borders.empty(0, 10));

        createFieldOption(panel, 0, Bundle.getString("loginDialog.label.host"));
        createFieldOption(panel, 1, Bundle.getString("loginDialog.label.port"));
        createCheckBoxOption(panel, 2, null, Collections.singletonList("Save"));

        return createDefaultPanel(panel);
    }

    @Override
    protected Integer getInsetLeft() {
        return 5;
    }

}

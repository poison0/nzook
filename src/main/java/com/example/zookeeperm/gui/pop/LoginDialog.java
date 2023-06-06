package com.example.zookeeperm.gui.pop;

import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.loginDataDto;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.project.DefaultProjectFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Collections;

/**
 * @auth nss
 */
public class LoginDialog extends AbstractDialog {
    /**
     * 对话框大小
     */
    private static final Integer WIDTH = 300;
    /**
     * 对话框大小
     */
    private static final Integer HEIGHT = 90;

    JTextField hostField;
    JTextField portField;
    JTextField timeoutField;

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

        hostField = createFieldOption(panel, 0, Bundle.getString("loginDialog.label.host"));
        portField = createFieldOption(panel, 1, Bundle.getString("loginDialog.label.port"));
        addValidatorByHost(portField);
        createCheckBoxOption(panel, 2, null, Collections.singletonList("Remember"));
//        createBlackLine(panel, 3);
//        JPanel buttonPanel = createNewLine(panel, 4);
//        createButton(buttonPanel, 0, Bundle.getString("loginDialog.button.testConnection"));

        return createDefaultPanel(panel);
    }

    public loginDataDto getLoginData() {
        loginDataDto loginData = new loginDataDto();
        loginData.setIp(hostField.getText());
        loginData.setPort(portField.getText());
        loginData.setTimeout(10000);
        return loginData;
    }

    private void addValidatorByHost(JTextField host) {
        new ComponentValidator(getDisposable()).withValidator(() -> {
            String pt = host.getText();
            if (StringUtil.isNotEmpty(pt)) {
                try {
                    int portValue = Integer.parseInt(pt);
                    if (portValue >= 0 && portValue <= 65535) {
                        return null;
                    } else {
                       return new ValidationInfo("The port number should be between 0 and 65535", host);
                    }
                } catch (NumberFormatException nfe) {
                    return new ValidationInfo("The port number should be between 0 and 65535", host);
                }
            } else {
                return null;
            }
        }).installOn(host);
        host.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(host).ifPresent(ComponentValidator::revalidate);
            }
        });

    }

    @Override
    protected Integer getInsetLeft() {
        return 5;
    }

}

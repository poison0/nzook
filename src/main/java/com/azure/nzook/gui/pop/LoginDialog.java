package com.azure.nzook.gui.pop;

import com.azure.nzook.constant.Constant;
import com.azure.nzook.data.CheckBoxOptionDto;
import com.azure.nzook.data.LoginDataDto;
import com.azure.nzook.util.Bundle;
import com.azure.nzook.util.DataUtils;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * @author niu
 * @version 1.0
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

    private ComponentValidator hostValidator;

    private ComponentValidator portValidator;

    JTextField hostField;
    JTextField portField;
    JBCheckBox saveCheckBox;
    JTextField timeoutField;

    public LoginDialog(String title) {
        super(title);
        init();
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
        LoginDataDto loginData = DataUtils.getCurrentLoginData();
        if (loginData.getPort() == null) {
            loginData.setPort(Constant.DEFAULT_PORT);
        }
        hostField = createFieldOption(panel, 0, Bundle.getString("loginDialog.label.host"),loginData.getIp());
        portField = createFieldOption(panel, 1, Bundle.getString("loginDialog.label.port"),loginData.getPort());
        addValidatorByPort(portField);
        addValidatorByHost(hostField);

        List<JBCheckBox> remember = createCheckBoxOption(panel, 2, null, Collections.singletonList(new CheckBoxOptionDto("Remember",true)));
        saveCheckBox = remember.get(0);
        return createDefaultPanel(panel);
    }

    public LoginDataDto getLoginData() {
        LoginDataDto loginData = new LoginDataDto();
        loginData.setIp(hostField.getText());
        loginData.setPort(portField.getText());
        loginData.setSave(saveCheckBox.isSelected());
        loginData.setTimeout(Constant.DEFAULT_TIMEOUT);
        return loginData;
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        if (StringUtil.isEmpty(hostField.getText())) {
            return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.host.empty"), hostField);
        }
        if (StringUtil.isEmpty(portField.getText())) {
            return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.port.empty"), portField);
        }
        if(hostValidator != null && hostValidator.getValidationInfo() != null){
            return hostValidator.getValidationInfo();
        }
        if(portValidator != null && portValidator.getValidationInfo() != null){
            return portValidator.getValidationInfo();
        }
        return null;
    }

    private void addValidatorByHost(JTextField host) {
        hostValidator = addValidatorEmptyByField(host, Bundle.getString("validationInfo.loginDialog.host.empty"));
    }
    private void addValidatorByPort(JTextField port) {
        portValidator = new ComponentValidator(getDisposable()).withValidator(() -> {
            String pt = port.getText();
            if (StringUtil.isNotEmpty(pt)) {
                try {
                    int portValue = Integer.parseInt(pt);
                    if (portValue >= 0 && portValue <= 65535) {
                        return null;
                    } else {
                        return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.port"), port);
                    }
                } catch (NumberFormatException nfe) {
                    return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.port"), port);
                }
            } else {
                return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.port.empty"), port);
            }
        }).installOn(port);
        port.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(port).ifPresent(ComponentValidator::revalidate);
            }
        });

    }

    @Override
    protected Integer getInsetLeft() {
        return 5;
    }

}

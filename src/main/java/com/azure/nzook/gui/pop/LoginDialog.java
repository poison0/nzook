package com.azure.nzook.gui.pop;

import com.azure.nzook.constant.LoginTypeEnum;
import com.azure.nzook.data.CheckBoxOptionDto;
import com.azure.nzook.data.logindata.*;
import com.azure.nzook.util.Bundle;
import com.azure.nzook.util.DataUtils;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBRadioButton;
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
 * @since 1.0
 */
public class LoginDialog extends AbstractDialog {
    /**
     * 对话框大小
     */
    private static final Integer WIDTH = 450;
    /**
     * 对话框大小
     */
    private static final Integer HEIGHT = 200;

    private ComponentValidator hostValidator;

    private ComponentValidator portValidator;

    private ComponentValidator connValidator;

    JTextField hostField;
    JTextField portField;
    JBCheckBox generalSaveCheckBox;
    JBRadioButton generalRadio;


    JTextField timeoutField;
    JTextField connStringField;
    JBCheckBox connSaveCheckBox;
    JBRadioButton connectionRadio;


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
        UserLoginData defaultLoginData = getDefaultLoginData();

        GeneralLoginData generalData =  defaultLoginData.getGeneralLoginData();
        ConnectStringLoginData connData = defaultLoginData.getConnectStringLoginData();

        generalRadio = createRadioButton(panel, 0, Bundle.getString("loginDialog.label.title.general"),true);
        generalRadio.setSelected(generalData.getUse());

        JPanel generalPanel = createSecondPanel(panel, 1);
        generalRadio.addChangeListener(e -> grayPanel(generalPanel, !generalRadio.isSelected()));

        hostField = createFieldOption(generalPanel, 0, Bundle.getString("loginDialog.label.host"),generalData.getIp());
        portField = createFieldOption(generalPanel, 1, Bundle.getString("loginDialog.label.port"),generalData.getPort());
        addValidatorByPort(portField);
        addValidatorByHost(hostField);
        List<JBCheckBox> generalRemember = createCheckBoxOption(generalPanel, 2, null, Collections.singletonList(new CheckBoxOptionDto(Bundle.getString("loginDialog.label.remember"),generalData.getSave())));
        grayPanel(generalPanel,!generalData.getUse());

        createBlackLine(panel,2);

        connectionRadio = createRadioButton(panel, 3, Bundle.getString("loginDialog.label.connect"),true);
        connectionRadio.setSelected(connData.getUse());

        JPanel connectPanel = createSecondPanel(panel, 4);
        connectionRadio.addChangeListener(e -> grayPanel(connectPanel, !connectionRadio.isSelected()));

        connStringField = createAreaOption(connectPanel, 0, Bundle.getString("loginDialog.label.title.connect"),connData.getConnectionString());
        List<JBCheckBox> connectRemember = createCheckBoxOption(connectPanel, 1, null, Collections.singletonList(new CheckBoxOptionDto(Bundle.getString("loginDialog.label.remember"), connData.getSave())));
        grayPanel(connectPanel,!connData.getUse());
        addValidatorByConn(connStringField);

        ButtonGroup bg = new ButtonGroup();
        bg.add(connectionRadio);
        bg.add(generalRadio);

        generalSaveCheckBox = generalRemember.get(0);
        connSaveCheckBox = connectRemember.get(0);
        return createDefaultPanel(panel);
    }

    private UserLoginData getDefaultLoginData() {
        UserLoginData loginData = DataUtils.getCurrentLoginData();
        if(loginData == null){
            loginData = new UserLoginData();
            GeneralLoginData generalLoginData = new GeneralLoginData();
            generalLoginData.setUse(true);
            loginData.setGeneralLoginData(generalLoginData);
            ConnectStringLoginData connectStringLoginData = new ConnectStringLoginData();
            loginData.setConnectStringLoginData(connectStringLoginData);
        }
        return loginData;
    }

    private void grayPanel(JPanel jPanel, boolean isGray) {
        for (Component component : jPanel.getComponents()) {
            component.setEnabled(!isGray);
            if (component instanceof JPanel children) {
                grayPanel(children,isGray);
            }
        }
    }

    public UserLoginData getLoginData() {
        UserLoginData loginData = new UserLoginData();
        GeneralLoginData generalLoginData = new GeneralLoginData();
        generalLoginData.setPort(portField.getText());
        generalLoginData.setIp(hostField.getText());
        generalLoginData.setUse(generalRadio.isSelected());
        generalLoginData.setLoginTypeEnum(LoginTypeEnum.GENERAL);
        generalLoginData.setSave(generalSaveCheckBox.isSelected());
        loginData.setGeneralLoginData(generalLoginData);
        ConnectStringLoginData connectStringLoginData = new ConnectStringLoginData();
        connectStringLoginData.setConnectionString(connStringField.getText());
        connectStringLoginData.setUse(connectionRadio.isSelected());
        connectStringLoginData.setLoginTypeEnum(LoginTypeEnum.CONNECTION_STRING);
        connectStringLoginData.setSave(connSaveCheckBox.isSelected());
        loginData.setConnectStringLoginData(connectStringLoginData);
        return loginData;
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        if(generalRadio.isSelected()) {
            if (StringUtil.isEmpty(hostField.getText())) {
                return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.host.empty"), hostField);
            }
            if (StringUtil.isEmpty(portField.getText())) {
                return new ValidationInfo(Bundle.getString("validationInfo.loginDialog.port.empty"), portField);
            }
            if (hostValidator != null && hostValidator.getValidationInfo() != null) {
                return hostValidator.getValidationInfo();
            }
            if (portValidator != null && portValidator.getValidationInfo() != null) {
                return portValidator.getValidationInfo();
            }
        }
        if (connectionRadio.isSelected()) {
            if(connValidator != null && connValidator.getValidationInfo() != null){
                return connValidator.getValidationInfo();
            }
        }
        return null;
    }

    private void addValidatorByHost(JTextField host) {
        hostValidator = addValidatorEmptyByField(host, Bundle.getString("validationInfo.loginDialog.host.empty"));
    }
    private void addValidatorByConn(JTextField connStringField) {
        connValidator = addValidatorEmptyByField(connStringField, Bundle.getString("validationInfo.loginDialog.conn.empty"));
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

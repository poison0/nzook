package com.azure.nzook.gui.pop;

import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.PermissionEnum;
import com.azure.nzook.data.UpdateNodeData;
import com.azure.nzook.util.Bundle;
import com.intellij.openapi.ui.ComboBoxWithWidePopup;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.util.ui.JBUI;
import org.apache.zookeeper.CreateMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niu
 * @since 1.0
 */
public class UpdateNode extends AbstractDialog {

    /**
     * 对话框大小
     */
    private static final Integer WIDTH = 500;
    /**
     * 对话框大小
     */
    private static final Integer HEIGHT = 260;

    private boolean isEdit = false;

    @Override
    protected Integer getPanelWidth() {
        return WIDTH;
    }

    @Override
    protected Integer getPanelHeight() {
        return HEIGHT;
    }
    private ComponentValidator ttlValidator;
    private ComponentValidator nodeNamevalidator;
    private JTextField parentNode;
    private JTextField nodeName;
    private ExpandableTextField nodeValue;
    private ComboBoxWithWidePopup<String> createMode;
    private JTextField ttl;
    private ComboBoxWithWidePopup<String> scheme;
    private JTextField id;
    private List<JBCheckBox> permissions;

    private final UpdateNodeData defaultData;

    public UpdateNode(String title) {
        super(title);
        defaultData = new UpdateNodeData();
        init();
    }

    public UpdateNode(String title, UpdateNodeData data,boolean isEdit) {
        super(title);
        defaultData = data;
        this.isEdit = isEdit;
        init();
    }


    @Override
    protected @Nullable JComponent createCenterPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel panel = new JPanel(gridBagLayout);
        panel.setBorder(JBUI.Borders.empty(0, 10));

        createTitledSeparator(panel, 0, Bundle.getString("updateNodeDialog.titledSeparator.basic"));
        if(!isEdit){
            parentNode = createFieldOption(panel, 1, Bundle.getString("updateNodeDialog.label.parentNode"),defaultData.getParentNodeName(), false);
        }
        nodeName = createFieldOption(panel, 2, Bundle.getString("updateNodeDialog.label.nodeName"),defaultData.getNodeName(),!isEdit);
        nodeNamevalidator = addValidatorNotEmpty(nodeName);
        nodeValue = createAreaOption(panel, 3, Bundle.getString("updateNodeDialog.label.nodeValue"),defaultData.getNodeValue());


        JPanel advancedPanel = createCollapsibleTitledSeparator(panel, 4, Bundle.getString("updateNodeDialog.titledSeparator.advanced"));

        if(!isEdit){
            createMode = createComboBoxOption(advancedPanel, 0, Bundle.getString("updateNodeDialog.label.createMode"), Constant.CREATE_MODE_OPTIONS, defaultData.getCreateMode().name());
            ttl = createFieldOption(advancedPanel, 0, 0, 1, Bundle.getString("updateNodeDialog.label.ttl"),
                    defaultData.getTtl() == null ? "" : defaultData.getTtl().toString(), false);
            addValidatorByTtl(ttl);
            addCreateModeListener(createMode, ttl);
        }

        scheme = createComboBoxOption(advancedPanel, 1, Bundle.getString("updateNodeDialog.label.scheme"), Constant.SCHEME_OPTIONS, defaultData.getScheme());
        id = createFieldOption(advancedPanel, 2, Bundle.getString("updateNodeDialog.label.id"),defaultData.getId(),false);
        addSchemeListener(scheme, id);


        permissions = createCheckBoxOption(advancedPanel, 3, Bundle.getString("updateNodeDialog.label.permissions"), defaultData.getPermsOptionList());


        return createDefaultPanel(panel);
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        if (StringUtil.isEmpty(nodeName.getText())) {
            return new ValidationInfo(Bundle.getString("updateNodeDialog.noteName.notEmpty.error"), parentNode);
        }
        if(ttlValidator != null && ttlValidator.getValidationInfo() != null){
            return ttlValidator.getValidationInfo();
        }
        if(nodeNamevalidator != null && nodeNamevalidator.getValidationInfo() != null){
            return nodeNamevalidator.getValidationInfo();
        }
        return null;
    }

    private void addCreateModeListener(ComboBoxWithWidePopup<String> createModeComboBox,JTextField ttlField) {
        createModeComboBox.addItemListener(e -> {
            Object selectedObject = e.getItemSelectable().getSelectedObjects()[0];
            ttlField.setEnabled(selectedObject.equals(CreateMode.PERSISTENT_WITH_TTL.name()) || selectedObject.equals(CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL.name()));
        });
    }
    private void addSchemeListener(ComboBoxWithWidePopup<String> schemeComboBox,JTextField idField) {
        schemeComboBox.addItemListener(e -> {
            Object selectedObject = e.getItemSelectable().getSelectedObjects()[0];
            if (selectedObject.equals("world")) {
                idField.setEnabled(false);
                idField.setText("anyone");
            }else if(selectedObject.equals("auth")){
                idField.setEnabled(false);
                idField.setText("");
            }else{
                idField.setEnabled(true);
                idField.setText("");
            }
        });
    }

    public UpdateNodeData getData() {
        UpdateNodeData data = new UpdateNodeData();
        data.setNodeName(nodeName.getText());
        data.setNodeValue(nodeValue.getText());
        if (!isEdit) {
            data.setParentNodeName(parentNode.getText());
            Object selectedItem = createMode.getSelectedItem();
            if (selectedItem != null) {
                data.setCreateMode(CreateMode.valueOf(selectedItem.toString()));
            }
            if (StringUtil.isNotEmpty(ttl.getText())) {
                data.setTtl(Long.parseLong(ttl.getText()));
            }
        }
        data.setScheme((String) scheme.getSelectedItem());
        data.setId(id.getText());
        List<PermissionEnum> permsList = new ArrayList<>();
        permissions.forEach(jbCheckBox -> {
            if (jbCheckBox.isSelected()) {
                permsList.add(PermissionEnum.getByName(jbCheckBox.getText()));
            }
        });
        data.setPermsList(permsList);
        return data;
    }

    public ComponentValidator addValidatorNotEmpty(JTextField field) {
        ComponentValidator validator = addValidatorEmptyByField(field, Bundle.getString("updateNodeDialog.noteName.notEmpty.error"));
        field.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(field).ifPresent(ComponentValidator::revalidate);
            }
        });
        return validator;
    }

    private void addValidatorByTtl(JTextField ttl) {
        ttlValidator = new ComponentValidator(getDisposable()).withValidator(() -> {
            String pt = ttl.getText();
            if (StringUtil.isNotEmpty(pt)) {
                try {
                    long l = Long.parseLong(pt);
                    if(l <= 0){
                        return new ValidationInfo(Bundle.getString("updateNodeDialog.ttl.error"), ttl);
                    }
                } catch (NumberFormatException nfe) {
                    return new ValidationInfo(Bundle.getString("updateNodeDialog.ttl.error"), ttl);
                }
            }
            return null;
        }).installOn(ttl);
        ttl.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(ttl).ifPresent(ComponentValidator::revalidate);
            }
        });
    }

}

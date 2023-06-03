package com.example.zookeeperm.gui.pop;

import com.example.zookeeperm.constant.PermissionEnum;
import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.ui.ComboBoxWithWidePopup;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.ui.dsl.builder.impl.CollapsibleTitledSeparator;
import com.intellij.util.ui.JBUI;
import kotlin.Unit;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author niu
 */
public class UpdateNode extends DialogWrapper {
    /**
     * 对话框大小
     */
    private static final Integer WIDTH = 450;
    /**
     * 对话框大小
     */
    private static final Integer HEIGHT = 160;
    /**
     * 选项离左边的距离
     */
    private static final Integer BORDER_LEFT = 15;
    /**
     * 标签和输入框的间距
     */
    private static final Integer INSET_LEFT = 15;
    /**
     * 多选框之间的间距
     */
    private static final Integer CHECK_INSET_RIGHT = 10;



    public UpdateNode(String title) {
        super(true);
        setTitle(title);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        GridBagLayout gridBagLayout = new GridBagLayout();

        JPanel panel = new JPanel(gridBagLayout);
        panel.setMaximumSize(new Dimension(600, 300));
        panel.setBorder(JBUI.Borders.empty(0, 10));

        createTitledSeparator(panel, 0, Bundle.getString("updateNodeDialog.titledSeparator.basic"));
        createFieldOption(panel, 1, Bundle.getString("updateNodeDialog.label.parentNode"));
        createFieldOption(panel, 2, Bundle.getString("updateNodeDialog.label.newNodeName"));
        createAreaOption(panel, 3, Bundle.getString("updateNodeDialog.label.newNodeValue"));


        JPanel advancedPanel = createCollapsibleTitledSeparator(panel, 4, Bundle.getString("updateNodeDialog.titledSeparator.advanced"));

        createComboBoxOption(advancedPanel, 0, Bundle.getString("updateNodeDialog.label.createMode"), new String[]{
                "PERSISTENT",
                "PERSISTENT_SEQUENTIAL",
                "EPHEMERAL",
                "EPHEMERAL_SEQUENTIAL"
        });
        createFieldOption(advancedPanel, 1, Bundle.getString("updateNodeDialog.label.scheme"));
        createFieldOption(advancedPanel, 2, Bundle.getString("updateNodeDialog.label.id"));
        createCheckBoxOption(advancedPanel, 3, Bundle.getString("updateNodeDialog.label.permissions"));

        dialogPanel.add(panel, BorderLayout.NORTH);
        dialogPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return dialogPanel;
    }

    private JPanel createCollapsibleTitledSeparator(JPanel panel, int gridy, String title) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 2;
        c.gridy = gridy;
        c.gridwidth = GridBagConstraints.REMAINDER;
        CollapsibleTitledSeparator collapsibleTitledSeparator = new CollapsibleTitledSeparator(title);
        collapsibleTitledSeparator.setExpanded(false);
        JPanel advancedPanel = new JPanel(new GridBagLayout());
        advancedPanel.setVisible(false);
        collapsibleTitledSeparator.onAction(show -> {
            advancedPanel.setVisible(show);
            return Unit.INSTANCE;
        });
        panel.add(collapsibleTitledSeparator,c);
        c.weighty = 4;
        c.gridy = gridy + 1;
        panel.add(advancedPanel, c);
        return advancedPanel;
    }

   /**
     * 创建分割线
     */
    private void createTitledSeparator(JPanel panel, int gridy, String title) {
        TitledSeparator titledSeparator = new TitledSeparator(title);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 2;
        c.gridy = gridy;
        c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(titledSeparator,c);
    }
    /**
     * 创建选择框选项
     */
    private ComboBoxWithWidePopup<String> createComboBoxOption(JPanel panel, int gridy, String title, String[] options) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        JBLabel label = new JBLabel(title+"：");
        c.anchor = GridBagConstraints.WEST;
        panel.add(label, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = JBUI.insetsLeft(INSET_LEFT);
        ComboBoxWithWidePopup<String> comboBox = new ComboBoxWithWidePopup<>(options);
        comboBox.setFocusable(false);
        panel.add(comboBox, c);
        return comboBox;
    }

    /**
     * 创建基本输入选项
     */
    private JTextField createFieldOption(JPanel panel, int gridy, String title) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JBLabel label = new JBLabel(title+"：");
        panel.add(label, c);
        c.insets = JBUI.insetsLeft(INSET_LEFT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        JTextField textField = new JTextField();
        panel.add(textField, c);
        return textField;
    }
    /**
     * 创建基本输入选项
     */
    private ExpandableTextField createAreaOption(JPanel panel, int gridy, String title) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JBLabel label = new JBLabel(title+"：");
        panel.add(label, c);
        c.insets = JBUI.insetsLeft(INSET_LEFT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        ExpandableTextField textArea = new ExpandableTextField();
        panel.add(textArea, c);
        return textArea;
    }
    /**
     * 创建多选框
     */
    private void createCheckBoxOption(JPanel panel, int gridy, String title) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JBLabel label = new JBLabel(title+"：");
        panel.add(label, c);
        c.insets = JBUI.insetsLeft(INSET_LEFT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        for (PermissionEnum value : PermissionEnum.values()) {
            JBCheckBox checkBox = new JBCheckBox(value.getName());
            checkBox.setBorder(JBUI.Borders.emptyRight(CHECK_INSET_RIGHT));
            checkBoxPanel.add(checkBox);
        }
        panel.add(checkBoxPanel, c);
    }
}

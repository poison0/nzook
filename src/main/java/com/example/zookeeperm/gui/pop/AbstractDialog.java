package com.example.zookeeperm.gui.pop;

import com.example.zookeeperm.data.CheckBoxOptionDto;
import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.ui.ComboBoxWithWidePopup;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.ui.dsl.builder.impl.CollapsibleTitledSeparator;
import com.intellij.util.ui.JBUI;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nss
 */
public abstract class AbstractDialog extends DialogWrapper {

    /**
     * 选项离左边的距离
     */
    private static final Integer BORDER_LEFT = 15;
    /**
     * 标签和输入框的间距
     */
    private static final Integer INSET_LEFT = 10;
    /**
     * 多选框之间的间距
     */
    private static final Integer CHECK_INSET_RIGHT = 10;

    protected AbstractDialog(String title) {
        super(true);
        setTitle(title);
        init();
    }

    protected JPanel createDefaultPanel(JPanel panel) {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.add(panel, BorderLayout.NORTH);
        dialogPanel.setPreferredSize(new Dimension(getPanelWidth(), getPanelHeight()));
        return dialogPanel;
    }

    protected Integer getInsetLeft() {
        return INSET_LEFT;
    }
    /**
     * 宽度
     */
    protected abstract Integer getPanelWidth();
    /**
     * 高度
     */
    protected abstract Integer getPanelHeight();

    /**
     * 创建可收起的标题分隔
     */
    protected JPanel createCollapsibleTitledSeparator(JPanel panel, int gridy, String title) {
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
     * 空校验
     */
    protected ComponentValidator addValidatorEmptyByField(JTextField field,String message) {
        ComponentValidator hostValidator = new ComponentValidator(getDisposable()).withValidator(() -> {
            String pt = field.getText();
            if (StringUtil.isEmpty(pt)) {
                return new ValidationInfo(message, field);
            }
            return null;
        }).installOn(field);
        field.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(field).ifPresent(ComponentValidator::revalidate);
            }
        });
        return hostValidator;
    }

    /**
     * 创建分割线
     */
    protected void createTitledSeparator(JPanel panel, int gridy, String title) {
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
    protected ComboBoxWithWidePopup<String> createComboBoxOption(JPanel panel, int gridy, String title, String[] options) {
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
        c.insets = JBUI.insetsLeft(getInsetLeft());
        ComboBoxWithWidePopup<String> comboBox = new ComboBoxWithWidePopup<>(options);
        comboBox.setFocusable(false);
        panel.add(comboBox, c);
        return comboBox;
    }
    /**
     * 创建基本输入选项
     */
    protected JTextField createFieldOption(JPanel panel, int gridy, String title) {
        return createFieldOption(panel,gridy,title,"");
    }
    /**
     * 创建基本输入选项
     */
    protected JTextField createFieldOption(JPanel panel, int gridy, String title,String defaultValue) {
        return createFieldOption(panel,gridy,0,1,title,defaultValue);
    }
    /**
     * 创建基本输入选项
     */
    protected JTextField createFieldOption(JPanel panel, int gridy,int firstWeightx,int secondWeightx, String title,String defaultValue) {
        return createFieldOption(panel,gridy,firstWeightx,secondWeightx,title,defaultValue,true);
    }
    /**
     * 创建基本输入选项
     */
    protected JTextField createFieldOption(JPanel panel, int gridy,int firstWeightx,int secondWeightx, String title,String defaultValue,boolean isEnable) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = firstWeightx;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JBLabel label = new JBLabel(title+"：");
        panel.add(label, c);
        c.insets = JBUI.insetsLeft(getInsetLeft());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = secondWeightx;
        JTextField textField = new JTextField();
        textField.setText(defaultValue);
        textField.setEnabled(isEnable);
        panel.add(textField, c);
        return textField;
    }

    /**
     * 创建基本输入选项
     */
    protected ExpandableTextField createAreaOption(JPanel panel, int gridy, String title) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JBLabel label = new JBLabel(title+"：");
        panel.add(label, c);
        c.insets = JBUI.insetsLeft(getInsetLeft());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        ExpandableTextField textArea = new ExpandableTextField();
        panel.add(textArea, c);
        return textArea;
    }
    protected void createBlackLine(JPanel panel, int gridy) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridy = gridy;
        c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(new JPanel(), c);
    }

    protected JPanel createNewLine(JPanel panel, int gridy) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridy = gridy;
        c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(buttonPanel, c);
        return buttonPanel;
    }

    /**
     * 创建button
     */
    protected JButton createButton(JPanel panel, int gridy,String buttonName) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JButton jButton = new JButton(buttonName);
        panel.add(jButton, c);
        return jButton;
    }
    /**
     * 创建多选框
     */
    protected List<JBCheckBox> createCheckBoxOption(JPanel panel, int gridy, String title, List<CheckBoxOptionDto> options) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridy = gridy;
        c.weightx = 0;
        c.insets = JBUI.insetsLeft(BORDER_LEFT);
        c.anchor = GridBagConstraints.WEST;
        JBLabel label;
        if (title != null) {
            label = new JBLabel(title + "：");
        }else {
            label = new JBLabel("");
        }
        panel.add(label, c);
        c.insets = JBUI.insetsLeft(getInsetLeft());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        List<JBCheckBox> checkBoxList = new ArrayList<>();
        for (CheckBoxOptionDto value : options) {
            JBCheckBox checkBox = new JBCheckBox(value.getValue());
            checkBox.setSelected(value.getCheck());
            checkBox.setBorder(JBUI.Borders.emptyRight(CHECK_INSET_RIGHT));
            checkBoxList.add(checkBox);
            checkBoxPanel.add(checkBox);
        }
        panel.add(checkBoxPanel, c);
        return checkBoxList;
    }

}

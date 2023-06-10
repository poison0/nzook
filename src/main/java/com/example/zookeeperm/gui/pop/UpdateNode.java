package com.example.zookeeperm.gui.pop;

import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.constant.PermissionEnum;
import com.example.zookeeperm.data.CheckBoxOptionDto;
import com.example.zookeeperm.util.Bundle;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author niu
 */
public class UpdateNode extends AbstractDialog {

    /**
     * 对话框大小
     */
    private static final Integer WIDTH = 500;
    /**
     * 对话框大小
     */
    private static final Integer HEIGHT = 160;


    public UpdateNode(String title) {
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



        createTitledSeparator(panel, 0, Bundle.getString("updateNodeDialog.titledSeparator.basic"));
        createFieldOption(panel, 1, Bundle.getString("updateNodeDialog.label.parentNode"));
        createFieldOption(panel, 2, Bundle.getString("updateNodeDialog.label.newNodeName"));
        createAreaOption(panel, 3, Bundle.getString("updateNodeDialog.label.newNodeValue"));


        JPanel advancedPanel = createCollapsibleTitledSeparator(panel, 4, Bundle.getString("updateNodeDialog.titledSeparator.advanced"));

        createComboBoxOption(advancedPanel, 0, Bundle.getString("updateNodeDialog.label.createMode"), Constant.CREATE_MODE_OPTIONS);
        createFieldOption(advancedPanel, 0,0,1, Bundle.getString("updateNodeDialog.label.ttl"),"",false);
        createComboBoxOption(advancedPanel, 1, Bundle.getString("updateNodeDialog.label.scheme"), Constant.SCHEME_OPTIONS);
        createFieldOption(advancedPanel, 2, Bundle.getString("updateNodeDialog.label.id"));
        createCheckBoxOption(advancedPanel, 3, Bundle.getString("updateNodeDialog.label.permissions"), Arrays.stream(PermissionEnum.values()).map(value->new CheckBoxOptionDto(value.getName())).collect(Collectors.toList()));

        return createDefaultPanel(panel);
    }


}

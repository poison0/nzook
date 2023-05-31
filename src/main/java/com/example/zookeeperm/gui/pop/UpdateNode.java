package com.example.zookeeperm.gui.pop;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBLabel;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.CheckBox;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author niu
 */
public class UpdateNode extends DialogWrapper {

    public UpdateNode() {
        super(true);
        setTitle("New Node");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        TitledSeparator titledSeparator = new TitledSeparator("Node Data");
        dialogPanel.add(titledSeparator, BorderLayout.NORTH);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWeights = new double[]{1.0};
        JPanel left = new JPanel(gridBagLayout);
        left.setBorder(JBUI.Borders.empty(0, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JBLabel label = new JBLabel("CreateMode");
        left.add(label, c);
        ComboBox<String> comboBox = new ComboBox<>(new String[]{
                "PERSISTENT",
                "PERSISTENT_SEQUENTIAL",
                "EPHEMERAL",
                "EPHEMERAL_SEQUENTIAL"
        });
        comboBox.setFocusable(false);
        comboBox.setMinimumAndPreferredWidth(400);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        left.add(comboBox, c);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        dialogPanel.add(left, BorderLayout.CENTER);
        dialogPanel.setMinimumSize(new Dimension(600, 300));
        dialogPanel.setPreferredSize(new Dimension(600, 300));

        return dialogPanel;
    }
}

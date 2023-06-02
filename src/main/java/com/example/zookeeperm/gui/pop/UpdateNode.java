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
        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel left = new JPanel(gridBagLayout);
        left.setMaximumSize(new Dimension(600, 300));
        left.setBorder(JBUI.Borders.empty(0, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.ipadx = 100;
        JBLabel label = new JBLabel("CreateMode: ");
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
        c.weightx = 1;
        left.add(comboBox, c);

        dialogPanel.add(left, BorderLayout.CENTER);
        dialogPanel.setMinimumSize(new Dimension(600, 300));
        dialogPanel.setPreferredSize(new Dimension(600, 300));

        return dialogPanel;
    }
}

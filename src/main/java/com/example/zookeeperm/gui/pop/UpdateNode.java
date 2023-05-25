package com.example.zookeeperm.gui.pop;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.border.CustomLineBorder;
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
        OnePixelSplitter onePixelSplitter = new OnePixelSplitter(false, 0.4f);
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.add(onePixelSplitter, BorderLayout.CENTER);

        JPanel left = new JPanel(new BorderLayout());
        JLabel label = new JLabel("left");
        left.add(label, BorderLayout.CENTER);

        onePixelSplitter.setFirstComponent(left);

        JPanel reght = new JPanel(new BorderLayout());
        JLabel rightLabel = new JLabel("rightLabel");
        reght.add(rightLabel, BorderLayout.CENTER);

        onePixelSplitter.setSecondComponent(reght);

        dialogPanel.setPreferredSize(new Dimension(400, 200));

        return dialogPanel;
    }
}

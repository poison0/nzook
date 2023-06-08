package com.example.zookeeperm.gui.pop;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author nss
 */
public class ConfirmDialog extends DialogWrapper {


    private final String message;

    public ConfirmDialog(@Nullable Project project, String title, String message, boolean canBeParent) {
        super(project, canBeParent);
        this.message = message;
        setTitle(title);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JLabel jLabel = new JLabel(this.message);
        jLabel.setMinimumSize(new Dimension(200, 50));
        return jLabel;
    }
}

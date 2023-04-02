package com.example.zookeeperm.gui;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import java.awt.*;

public class DataPane extends JBPanel<DataPane> {
    private JBLabel headerLabel = new JBLabel();
    private JBPanel<HttpView> descriptionPanel = new JBPanel<>(new BorderLayout());

    public DataPane() {
        super();
        setLayout(new BorderLayout());
        headerLabel.setIcon(AllIcons.General.BalloonInformation);
        headerLabel.setFont(UIUtil.getLabelFont().deriveFont((UIUtil.getLabelFont().getSize2D() + JBUIScale.scale(2))).deriveFont(Font.BOLD));
        headerLabel.setBorder(JBUI.Borders.empty(5, 10));
        ////--------
        headerLabel.setText("/zookeepe");
        ////--------
        add(headerLabel, BorderLayout.NORTH);
        HttpView httpView = new HttpView();
        httpView.updateHtml("<html><body><h1>test</h1></body></html>");
        descriptionPanel.add(httpView, BorderLayout.CENTER);

        add(descriptionPanel, BorderLayout.CENTER);

    }
}

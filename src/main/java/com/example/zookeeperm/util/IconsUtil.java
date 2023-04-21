package com.example.zookeeperm.util;

import com.intellij.icons.AllIcons;
import com.intellij.util.ui.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author nss
 */
public class IconsUtil {

    private IconsUtil() {
    }

    public static final Icon GRAY_MODELS = grayIcon(AllIcons.Nodes.Models);

    public static Icon grayIcon(Icon icon) {
        BufferedImage bufferedImage = ImageUtil.createImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        icon.paintIcon(null, g2, 0, 0);
        g2.dispose();
        return new ImageIcon(GrayFilter.createDisabledImage((new ImageIcon(bufferedImage)).getImage()));
    }
}

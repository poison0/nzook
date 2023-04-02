package com.example.zookeeperm.gui;

import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.util.DateUtils;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.treeView.NodeRenderer;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeCell extends NodeRenderer {

    @Override
    public void customizeCellRenderer(@NotNull JTree jTree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) value;
        Object userObject = defaultMutableTreeNode.getUserObject();
        if (userObject instanceof NodeData) {
            NodeData nodeData = (NodeData) userObject;
            appendType(nodeData.getMetaData().getEphemeralOwner(),leaf);
            append(nodeData.getNodeValue(),SimpleTextAttributes.REGULAR_ATTRIBUTES, true);
            if (!leaf) {
                appendCount(defaultMutableTreeNode.getChildCount());
            }
            appendCreateTime(nodeData.getMetaData().getCtime());
        }
    }


    private void appendCreateTime(long createTime) {
        if (createTime != 0) {
            append("   "+DateUtils.toAge(createTime)+"...", SimpleTextAttributes.GRAY_ATTRIBUTES);
        }else {
            setIcon(AllIcons.Nodes.Module);
        }
    }

    private void appendCount(int count) {
        String countStr = count + " nodes";
        if (count == 1) {
            countStr = "1 node";
        }
        append(" ("+countStr+")", SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES);
    }

    private void appendType(long type,boolean leaf) {
        if (type == 0) {
            setIcon(AllIcons.Nodes.IdeaProject);
        } else {
            setIcon(AllIcons.Nodes.Module);
        }
    }
}

package com.example.zookeeperm.util;

import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.gui.PathTree;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;

/**
 * @auth nss
 */
public class ExpandUtil {
    /**
     * 展开或关闭tree视图
     */
    public static void expandAll(PathTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration<?> e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree,path, expand);
            }
        }
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
}

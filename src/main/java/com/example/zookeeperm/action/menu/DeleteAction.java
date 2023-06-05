package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.gui.pop.ConfirmDialog;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.SimpleNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class DeleteAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {


        ConfirmDialog dialog = new ConfirmDialog(anActionEvent.getProject(), Bundle.getString("action.DeleteAction.text"),"Confirm deletion of node 'node'",true);
        if (dialog.showAndGet()) {
            System.out.println("delete");
//            TreeSelectionModel selectionModel = OperationWindow.tree.getSelectionModel();
//            OperationWindow.tree.removeSelectionPath(selectionModel.getSelectionPath());
            DefaultTreeModel model = (DefaultTreeModel)OperationWindow.tree.getModel();
            TreeSelectionModel selectionModel = OperationWindow.tree.getSelectionModel();
            System.out.println(model);
        }

    }
    public DeleteAction() {
        super(Bundle.getString("action.DeleteAction.text"), Bundle.getString("action.DeleteAction.description"), AllIcons.General.Remove);
    }
}

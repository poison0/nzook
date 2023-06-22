package com.azure.nzook.action.toolbar;

import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.ZookeeperData;
import com.azure.nzook.service.Login;
import com.azure.nzook.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author niu
 * @since 1.0
 */
public class ExecuteAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        Login.popupLoginDialog(project);
    }



    public ExecuteAction() {
        super(Bundle.getString("action.ExecuteAction.text"), Bundle.getString("action.ExecuteAction.description"), AllIcons.General.Add);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return ZookeeperData.getStatus() == StatusEnum.NOT_CONNECT;
    }
}

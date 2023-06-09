package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.LoginDataDto;
import com.example.zookeeperm.gui.pop.LoginDialog;
import com.example.zookeeperm.service.Login;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author niu
 */
public class ExecuteAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        Login.popupLoginDialog(project);
    }



    public ExecuteAction() {
        super(Bundle.getString("action.ExecuteAction.text"), Bundle.getString("action.ExecuteAction.description"), AllIcons.Toolbar.AddSlot);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.NOT_CONNECT;
    }
}

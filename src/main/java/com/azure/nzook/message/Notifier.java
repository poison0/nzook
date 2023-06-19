package com.azure.nzook.message;

import com.intellij.notification.*;
import com.intellij.openapi.project.DefaultProjectFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

/**
 * @author niu
 * @since 1.0
 */
public class Notifier {

    private Notifier() {
    }
    //通知弹框
    public static void notify(String content,MessageType type) {
        Project defaultProject = DefaultProjectFactory.getInstance().getDefaultProject();
        NotificationGroupManager.getInstance().getNotificationGroup("notify").createNotification(content,type).notify(defaultProject);
    }
}

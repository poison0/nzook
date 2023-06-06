package com.example.zookeeperm.message;

import com.intellij.notification.*;
import com.intellij.openapi.project.DefaultProjectFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

/**
 * 异常弹框
 * @author niu
 */
public class Notifier {
    //通知弹框
    public static void notify(String content,MessageType type) {
        Project defaultProject = DefaultProjectFactory.getInstance().getDefaultProject();
        NotificationGroupManager.getInstance().getNotificationGroup("notify").createNotification(content,type).notify(defaultProject);
    }
}

package com.example.etcnotification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.*;

public class SaveNotificationPlugin implements VirtualFileListener {
    private static int saveCount = 0;

    public static void main(String[] args) {
        VirtualFileManager.getInstance().addVirtualFileListener(new SaveNotificationPlugin());
    }

    @Override
    public void contentsChanged(VirtualFileEvent event) {
        VirtualFile file = event.getFile();
        if (!file.isDirectory()) {
            saveCount++;
            if (saveCount == 10) {
                saveCount = 0;
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                for (Project project : projects) {
                    Notification notification = new Notification("Save Notification",
                            "File saved",
                            "Easier To Change? (ETC)",
                            NotificationType.INFORMATION);
                    Notifications.Bus.notify(notification, project);
                }
            }
        }
    }
}

package com.example.zookeeperm;

import com.example.zookeeperm.service.Login;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class helloAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        try {
            Login.login();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (KeeperException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}

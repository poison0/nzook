package com.example.zookeeperm.action.toolbar;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.NlsActions;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author niu
 */
public abstract class AbstractAction extends AnAction {

    protected AbstractAction(@Nullable @NlsActions.ActionText String text, @Nullable @NlsActions.ActionDescription String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void update(AnActionEvent e) {
        var p = e.getProject();
        if (p == null || !p.isInitialized() || p.isDisposed()) {
            e.getPresentation().setEnabled(false);
        } else {
            e.getPresentation().setEnabled(isEnabled(e));
        }
    }

    protected abstract boolean isEnabled(AnActionEvent e);
}

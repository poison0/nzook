package com.azure.nzook.util;

import com.intellij.AbstractBundle;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author nss
 */
public class Bundle extends AbstractBundle {
    public static final String BUNDLE = "messages.ZookeeperBundle";
    private static final Bundle INSTANCE = new Bundle(BUNDLE);

    public Bundle(@NonNls @NotNull String pathToBundle) {
        super(pathToBundle);
    }

    @Nls
    @NotNull
    public static String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    @Nls
    @NotNull
    public static String getString(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return message(key, params);
    }

    @Override
    protected ResourceBundle findBundle(@NotNull @NonNls String pathToBundle,
                                        @NotNull ClassLoader loader,
                                        @NotNull ResourceBundle.Control control) {
        final String chineseLanguagePlugin = "com.intellij.zh";
        if (!PluginManager.isPluginInstalled(PluginId.getId(chineseLanguagePlugin))) {
            return ResourceBundle.getBundle(pathToBundle, Locale.ROOT, loader, control);
        }
        return ResourceBundle.getBundle(pathToBundle, Locale.getDefault(), loader, control);
    }
}

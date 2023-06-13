package icons;

import com.intellij.ide.presentation.Presentation;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @auth nss
 */
@Presentation
public interface Icons {
    Icon nzookIcon = IconLoader.getIcon("/META-INF/nzookIcon.svg",Icons.class);
}

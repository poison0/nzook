package icons;

import com.intellij.ide.presentation.Presentation;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author niu
 * @version 1.0
 */
@Presentation
public interface Icons {
    Icon nzookIcon = IconLoader.getIcon("/META-INF/nzookIcon.svg",Icons.class);
}

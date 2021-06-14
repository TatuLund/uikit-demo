package org.vaadin.uikit;

import org.vaadin.uikit.components.layout.UkAppLayout;

import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;

@Push
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
public class MainLayout extends UkAppLayout implements RouterLayout {

    public MainLayout() {
        setMenu();
        setLogo("Demo application");
        setLogout();
    }

}

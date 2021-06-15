package org.vaadin.uikit;

import org.vaadin.uikit.components.layout.UkAppLayout;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;

@StyleSheet("context://custom.css")
//@StyleSheet("context://uikit.min.css")
@JavaScript("context://uikit.min.js")
@JavaScript("context://uikit-icons.min.js")
@Push
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
public class MainLayout extends UkAppLayout implements RouterLayout {

    public MainLayout() {
        setMenu("",MenuType.BAR,true);
        setLogo("Demo application");
        setLogout();
    }

}

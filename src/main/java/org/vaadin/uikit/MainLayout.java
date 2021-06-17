package org.vaadin.uikit;

import org.vaadin.uikit.components.layout.UkAppLayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;

@StyleSheet("context://custom.css")
//@StyleSheet("context://uikit.min.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.min.js")
@Push
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
public class MainLayout extends UkAppLayout implements RouterLayout, BeforeEnterObserver {

    public MainLayout() {
        setMenu();
        setLogo("Demo application");
        setLogout();
        getLogout().addClickListener(event -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().navigate("login");            
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Boolean loggedIn = (Boolean) VaadinSession.getCurrent()
                .getAttribute("loggedIn");
        String route = event.getLocation().getPath();
        if (!route.equals("login")) {
            if (loggedIn == null || !loggedIn) {
                VaadinSession.getCurrent().setAttribute("intendedRoute",
                        route);
                event.rerouteTo("login");
            }
        } else if (loggedIn != null && loggedIn) {
            event.forwardTo("");
        }        
    }

}

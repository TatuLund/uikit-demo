package org.vaadin.uikit;

import org.vaadin.uikit.components.UkLogin;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.UkNotification.Position;
import org.vaadin.uikit.components.UkNotification.Status;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

//@StyleSheet("context://custom.css")
@StyleSheet("context://uikit.min.css")
@JavaScript("context://uikit.min.js")
@JavaScript("context://uikit-icons.min.js")
@PageTitle("Login")
@Route("login")
public class LoginView extends UkLogin {

    public LoginView() {
        super();
        addLoginListener(event -> {
            if (event.getUsername().equals("user")
                    && event.getPassword().equals("user")) {
                String route = (String) VaadinSession.getCurrent()
                        .getAttribute("intendedRoute");
                VaadinService
                        .reinitializeSession(VaadinService.getCurrentRequest());
                VaadinSession.getCurrent().setAttribute("loggedIn", true);
                if (route != null && !route.equals("route")) {
                    UI.getCurrent().navigate(route);
                } else {
                    UI.getCurrent().navigate("");
                }
            }
        });
        addForgotPasswordListener(event -> {
            UkNotification.show("User name: user, Password: user",
                    Status.PRIMARY, Position.TOP_CENTER, 2000);
        });
    }

}

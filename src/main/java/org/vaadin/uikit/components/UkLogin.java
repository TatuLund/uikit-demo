package org.vaadin.uikit.components;

import org.vaadin.uikit.components.UkButton.ButtonSize;
import org.vaadin.uikit.components.UkButton.ButtonVariant;
import org.vaadin.uikit.components.UkCard.CardVariant;
import org.vaadin.uikit.components.input.UkPasswordField;
import org.vaadin.uikit.components.input.UkTextField;
import org.vaadin.uikit.components.interfaces.UkMargin.MarginSide;
import org.vaadin.uikit.components.interfaces.UkMargin.MarginSize;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.layout.UkForm;
import org.vaadin.uikit.components.layout.UkFlex.Direction;
import org.vaadin.uikit.components.layout.UkFlex.HorizontalAlignment;
import org.vaadin.uikit.components.layout.UkFlex.VerticalAlignment;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkLogin extends Composite<UkFlex> {

    private UkFlex flex = new UkFlex();
    private UkCard card = new UkCard();
    private UkTextField nameField = new UkTextField();
    private UkPasswordField passwordField = new UkPasswordField();
    private UkButton loginButton = new UkButton("Login");
    private UkButton forgotButton = new UkButton("Forgot password");
    private String loginTitle;
    private String nameLabel;
    private String passwordLabel;

    public UkLogin() {
        this(null, null, null, null, null);
    }

    public UkLogin(String loginTitle, String nameLabel, String passwordLabel,
            String loginLabel, String forgotLabel) {
        this.loginTitle = loginTitle == null ? "Login" : loginTitle;
        this.nameLabel = nameLabel == null ? "User name" : nameLabel;
        this.passwordLabel = passwordLabel == null ? "Password" : passwordLabel;
        if (loginLabel != null)
            loginButton.setText(loginLabel);
        if (forgotLabel != null)
            forgotButton.setText(forgotLabel);
        loginButton.addClickListener(event -> {
            fireIfValid();
        });
        nameField.addKeyPressListener(Key.ENTER, event -> {
            passwordField.focus();
        });
        passwordField.addKeyPressListener(Key.ENTER, event -> {
            fireIfValid();
        });
        forgotButton.addClickListener(event -> {
            fireEvent(new ForgotPasswordEvent(this, true));
        });
    }

    private void fireIfValid() {
        if (!nameField.getValue().isEmpty()
                && !passwordField.getValue().isEmpty()) {
            fireEvent(new LoginEvent(this, true, nameField.getValue(),
                    passwordField.getValue()));
        }
    }

    public Registration addLoginListener(
            ComponentEventListener<LoginEvent> listener) {
        return addListener(LoginEvent.class, listener);
    }

    public Registration addForgotPasswordListener(
            ComponentEventListener<ForgotPasswordEvent> listener) {
        return addListener(ForgotPasswordEvent.class, listener);
    }

    @Override
    public void onAttach(AttachEvent event) {
        nameField.focus();
    }

    @Override
    public UkFlex initContent() {
        flex.setDirection(Direction.COLUMN);
        flex.setVerticalAlignment(VerticalAlignment.MIDDLE);
        flex.setHorizontalAlignment(HorizontalAlignment.AROUND);
        flex.setSizeFull();
        UkForm form = new UkForm();
        form.add(nameLabel, nameField);
        form.add(passwordLabel, passwordField);
        loginButton.setMargin();
        loginButton.setSize(ButtonSize.SMALL);
        forgotButton.setVariant(ButtonVariant.TEXT);
        forgotButton.setMargin(MarginSize.LARGE, MarginSide.RIGHT);
        form.add(forgotButton, loginButton);
        card.setTitle(loginTitle);
        card.setContent(form);
        card.setVariant(CardVariant.PRIMARY);
        flex.add(card);
        return flex;
    }

    @SuppressWarnings("serial")
    public static class ForgotPasswordEvent extends ComponentEvent<UkLogin> {
        public ForgotPasswordEvent(UkLogin source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    @SuppressWarnings("serial")
    public static class LoginEvent extends ComponentEvent<UkLogin> {

        private String username;
        private String password;

        public LoginEvent(UkLogin source, boolean fromClient, String username,
                String password) {
            super(source, fromClient);
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}

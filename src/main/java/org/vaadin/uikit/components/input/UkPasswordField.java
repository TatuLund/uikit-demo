package org.vaadin.uikit.components.input;

public class UkPasswordField extends InputBase {

    public UkPasswordField() {
        addClassName("uk-input");
        getElement().setAttribute("type", "password");
    }

}

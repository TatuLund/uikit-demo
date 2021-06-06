package org.vaadin.uikit.components.input;

public class UkTextField extends InputBase {

    public UkTextField() {
        addClassName("uk-input");
    }

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }
}

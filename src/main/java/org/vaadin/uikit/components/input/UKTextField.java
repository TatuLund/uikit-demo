package org.vaadin.uikit.components.input;

public class UKTextField extends AbstractInput {

    public UKTextField() {
        addClassName("uk-input");
    }

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }
}

package org.vaadin.uikit.components.input;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.html.Input;

@SuppressWarnings("serial")
public abstract class InputBase extends Input implements UkField, KeyNotifier {

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }
}

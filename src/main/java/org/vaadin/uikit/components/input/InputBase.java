package org.vaadin.uikit.components.input;

import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkTooltip;
import org.vaadin.uikit.components.interfaces.UkValidation;

import com.vaadin.flow.component.html.Input;

public abstract class InputBase extends Input implements UkValidation, UkTooltip, UkMargin, UkBorder {

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }
}

package org.vaadin.uikit.components.input;

import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkFormSizing;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkTooltip;
import org.vaadin.uikit.components.interfaces.UkValidation;

/**
 * A component based on a native textarea html element with UIkit styles. 
 */
@SuppressWarnings("serial")
public class UkTextArea extends TextAreaBase
        implements UkValidation, UkTooltip, UkMargin, UkPadding, UkFormSizing, UkBorder {

    public UkTextArea() {
        addClassName("uk-textarea");
    }

    public void setResizeable(boolean resizeable) {
        if (resizeable) {
            getElement().getStyle().set("resize", "none");
        } else {
            getElement().getStyle().set("resize", "auto");
        }
    }

    public void setRows(int rows) {
        if (rows > 0) {
            getElement().setProperty("rows", "" + rows);
        }
    }

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }
}

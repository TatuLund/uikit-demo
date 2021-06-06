package org.vaadin.uikit.components.input;

import org.vaadin.uikit.interfaces.UkBorder;
import org.vaadin.uikit.interfaces.UkFormSizing;
import org.vaadin.uikit.interfaces.UkMargin;
import org.vaadin.uikit.interfaces.UkPadding;
import org.vaadin.uikit.interfaces.UkTooltip;
import org.vaadin.uikit.interfaces.UkValidation;

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

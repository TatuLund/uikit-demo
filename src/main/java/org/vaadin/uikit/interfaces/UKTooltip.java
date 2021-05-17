package org.vaadin.uikit.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UKTooltip extends HasElement {

    public default void setTooltip(String tooltip) {
        if (tooltip != null && !tooltip.isEmpty()) {
            getElement().setAttribute("uk-tooltip", tooltip);
        } else {
            getElement().removeAttribute("uk-tooltip");
        }
    }
}
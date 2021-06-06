package org.vaadin.uikit.components;

import org.vaadin.uikit.interfaces.UkMargin;
import org.vaadin.uikit.interfaces.UkTooltip;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.shared.Registration;

public class UkIcon extends Composite<Span> implements UkTooltip, UkMargin {
    Span icon = new Span();

    public UkIcon(UkIcons iconName) {
        icon.getElement().setAttribute("uk-icon", iconName.getIconName());
    }

    public UkIcon(UkIcons iconName, int ratio) {
        icon.getElement().setAttribute("uk-icon",
                "icon: " + iconName.getIconName() + ", ratio: " + ratio);
    }

    public void setButtonStyle(boolean buttonStyle) {
        if (buttonStyle) {
            icon.addClassName("uk-icon-button");
        } else {
            icon.removeClassName("uk-icon-button");
        }
    }

    public Registration addClickListener(
            ComponentEventListener<ClickEvent<Span>> listener) {
        return icon.addClickListener(listener);
    }

    @Override
    protected Span initContent() {
        return icon;
    }
}

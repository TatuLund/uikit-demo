package org.vaadin.uikit.components;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKTooltip;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.shared.Registration;

public class UKIcon extends Composite<Span> implements UKTooltip, UKMargin {
    Span icon = new Span();

    public UKIcon(UKIcons iconName) {
        icon.addClassName("uk-margin-small-right");
        icon.getElement().setAttribute("uk-icon", iconName.getIconName());
    }

    public UKIcon(UKIcons iconName, int ratio) {
        icon.addClassName("uk-margin-small-right");
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

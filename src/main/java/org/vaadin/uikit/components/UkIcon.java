package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkBoxShadow;
import org.vaadin.uikit.components.interfaces.UkFloat;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkTooltip;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkIcon extends Composite<Span>
        implements UkTooltip, UkMargin, UkFloat, UkHidden, UkBoxShadow {
    Span icon = new Span();

    public UkIcon(UkIcons iconName) {
        icon.getElement().setAttribute("uk-icon", iconName.getIconName());
    }

    public UkIcon(UkIcons iconName, double ratio) {
        icon.getElement().setAttribute("uk-icon",
                "icon: " + iconName.getIconName() + "; ratio: " + ratio);
    }

    public void setButtonStyle(boolean buttonStyle) {
        if (buttonStyle) {
            icon.removeClassName("uk-icon-link");
            icon.addClassName("uk-icon-button");
        } else {
            icon.removeClassName("uk-icon-button");
        }
    }

    public void setLinkStyle(boolean linkStyle) {
        if (linkStyle) {
            icon.addClassName("uk-icon-link");
            icon.removeClassName("uk-icon-button");
        } else {
            icon.removeClassName("uk-icon-link");
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

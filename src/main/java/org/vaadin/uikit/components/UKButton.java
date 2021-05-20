package org.vaadin.uikit.components;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKTooltip;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.NativeButton;

public class UKButton extends NativeButton implements UKTooltip, UKMargin {

    public enum ButtonVariant {
        DEFAULT("uk-button-default"),
        PRIMARY("uk-button-primary"),
        SECONDARY("uk-button-secondary"),
        DANGER("uk-button-danger"),
        TEXT("uk-button-text"),
        LINK("uk-button-link");

        private final String variant;

        ButtonVariant(String variant) {
            this.variant = variant;
        }

        public String getVariantName() {
            return variant;
        }
    }

    public UKButton() {
        super();
        getElement().setAttribute("type", "button");
        addClassNames("uk-button", "uk-button-default");        
    }

    public UKButton(String text) {
        this();
        setText(text);
    }

    public UKButton(String text,
            ComponentEventListener<ClickEvent<NativeButton>> clickListener) {
        this(text);
        addClickListener(clickListener);
    }

    public void setVariant(ButtonVariant buttonVariant) {
        for (ButtonVariant variant : ButtonVariant.values()) {
            removeClassName(variant.getVariantName());
        }
        addClassName(buttonVariant.getVariantName());
    }
}

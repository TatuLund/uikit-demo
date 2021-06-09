package org.vaadin.uikit.components;

import org.vaadin.uikit.interfaces.UkBorder;
import org.vaadin.uikit.interfaces.UkFloat;
import org.vaadin.uikit.interfaces.UkMargin;
import org.vaadin.uikit.interfaces.UkTooltip;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.NativeButton;

public class UkButton extends NativeButton implements UkTooltip, UkMargin, UkBorder, UkFloat {

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

    public enum ButtonSize {
        SMALL("uk-button-small"),
        MEDIUM(null),
        LARGE("uk-button-large");

        private final String size;

        ButtonSize(String variant) {
            this.size = variant;
        }

        public String getButtonSize() {
            return size;
        }
    }
      
    private String caption;
    private UkIcon icon;

    public UkButton() {
        super();
        getElement().setAttribute("type", "button");
        addClassNames("uk-button", "uk-button-default");        
    }

    public UkButton(String text) {
        this();
        this.caption = text;
        setText(text);
    }

    public UkButton(UkIcon icon) {
        this();
        setIcon(icon);
        setSize(ButtonSize.SMALL);
    }

    public UkButton(String text,
            ComponentEventListener<ClickEvent<NativeButton>> clickListener) {
        this(text);
        addClickListener(clickListener);
    }

    public void setIcon(UkIcon icon) {
        this.icon= icon;
        removeAll();
        add(icon);
        if (caption != null && !caption.isEmpty()) {
            add(caption);
        }
    }

    public void setText(String text) {
        this.caption = text;
        if (icon == null) {
            super.setText(text);
        } else {
            setIcon(icon);
        }
    }

    public void setSize(ButtonSize size) {
        for (ButtonSize s : ButtonSize.values()) {
            if (s.getButtonSize() != null)
                getElement().getClassList().remove(s.getButtonSize());
        }
        if (size != ButtonSize.MEDIUM)
            getElement().getClassList().add(size.getButtonSize());
    }    

    public void setVariant(ButtonVariant buttonVariant) {
        for (ButtonVariant variant : ButtonVariant.values()) {
            removeClassName(variant.getVariantName());
        }
        addClassName(buttonVariant.getVariantName());
    }
}

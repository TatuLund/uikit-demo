package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkBoxShadow;
import org.vaadin.uikit.components.interfaces.UkFloat;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkTooltip;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.NativeButton;

/**
 * A button component based on NativeButton with UIkit styles.
 */
@SuppressWarnings("serial")
public class UkButton extends NativeButton implements UkTooltip, UkMargin,
        UkBorder, UkFloat, UkHidden, UkBoxShadow {

    public enum ButtonVariant {
        // @formatter:off
        /**
         * Default button style.
         */
        DEFAULT("uk-button-default"),
        /**
         * Indicates the primary action.
         */
        PRIMARY("uk-button-primary"),
        /**
         * Indicates an important action.
         */
        SECONDARY("uk-button-secondary"),
        /**
         * Indicates a dangerous or negative action.
         */
        DANGER("uk-button-danger"),
        /**
         * Applies an alternative, typographic style.
         */
        TEXT("uk-button-text"),
        /**
         * Makes a <button> look like an <a> element.
         */
        LINK("uk-button-link");
        // @formatter:on

        private final String variant;

        ButtonVariant(String variant) {
            this.variant = variant;
        }

        public String getVariantName() {
            return variant;
        }
    }

    public enum ButtonSize {
        // @formatter:off
        SMALL("uk-button-small"),
        MEDIUM(null),
        LARGE("uk-button-large");
        // @formatter:on

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

    /**
     * Default constructor creates button with default style without caption.
     */
    public UkButton() {
        super();
        getElement().setAttribute("type", "button");
        addClassNames("uk-button", "uk-button-default");
    }

    /**
     * Create button with caption text.
     * 
     * @param text
     *            The caption text.
     */
    public UkButton(String text) {
        this();
        this.caption = text;
        setText(text);
    }

    /**
     * Create a button with an icon, but without caption.
     * 
     * @param icon
     *            The icon used the in the button.
     */
    public UkButton(UkIcon icon) {
        this();
        setIcon(icon);
        setSize(ButtonSize.SMALL);
    }

    /**
     * A convenience constructor for a button with a caption text and a click
     * listener.
     * 
     * @param text
     *            The caption text.
     * @param clickListener
     *            The click listener.
     */
    public UkButton(String text,
            ComponentEventListener<ClickEvent<NativeButton>> clickListener) {
        this(text);
        addClickListener(clickListener);
    }

    /**
     * Set the icon in the button, preserves the caption if such exists. The
     * icon will be placed before the text.
     * 
     * @param icon
     *            The icon used in the button.
     */
    public void setIcon(UkIcon icon) {
        this.icon = icon;
        removeAll();
        add(icon);
        if (caption != null && !caption.isEmpty()) {
            add(caption);
        }
    }

    /**
     * Set the caption in the button, preserves the icon if such exists. The
     * text will be placed after the icon.
     * 
     * @param text
     *            The caption text used in the button.
     */
    public void setText(String text) {
        this.caption = text;
        if (icon == null) {
            super.setText(text);
        } else {
            setIcon(icon);
        }
    }

    /**
     * Set the size modifier.
     *
     * @param size
     *            The size modifier.
     */
    public void setSize(ButtonSize size) {
        for (ButtonSize s : ButtonSize.values()) {
            if (s.getButtonSize() != null)
                getElement().getClassList().remove(s.getButtonSize());
        }
        if (size != ButtonSize.MEDIUM)
            getElement().getClassList().add(size.getButtonSize());
    }

    /**
     * Set the button variant.
     * 
     * @param buttonVariant
     *            The button variant.
     */
    public void setVariant(ButtonVariant buttonVariant) {
        for (ButtonVariant variant : ButtonVariant.values()) {
            removeClassName(variant.getVariantName());
        }
        addClassName(buttonVariant.getVariantName());
    }
}

package org.vaadin.uikit.components;

import org.vaadin.uikit.components.UkButton.ButtonVariant;
import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkFloat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkDropdown extends Composite<Div> implements UkFloat, UkBorder {

    public enum Position {
        // @formatter:off
        TOP_JUSTIFY("top-justify"),
        TOP_LEFT("top-left"),
        TOP_CENTER("top-center"),
        TOP_RIGHT("top-right"),
        BOTTOM_JUSTIFY("bottom-justify"),
        BOTTOM_LEFT("bottom-left"),
        BOTTOM_CENTER("bottom-center"),
        BOTTOM_RIGHT("bottom-right"),
        LEFT_TOP("left-top"),
        LEFT_CENTER("left-center"),
        LEFT_BOTTOM("left-bottom"),
        RIGHT_TOP("right-top"),
        RIGHT_CENTER("right-center"),
        RIGHT_BOTTOM("right-bottom");
        // @formatter:on

        private final String position;

        Position(String position) {
            this.position = position;
        }

        public String getPosition() {
            return position;
        }
    }

    UkButton button = new UkButton();
    Div div = new Div();
    Div dropdown = new Div();
    Position position = Position.BOTTOM_LEFT;
    String mode;
    int delayHide = 800;
    int duration = 200;
    boolean flip = true;

    public UkDropdown() {
        this(false);
    }

    public UkDropdown(boolean click) {
        if (click) {
            mode = "click";
            sendSettings();
        } else {
            mode = "hover";
            sendSettings();
        }
    }

    public Registration addDropdownHiddenListener(
            ComponentEventListener<DropdownHiddenEvent> listener) {
        return addListener(DropdownHiddenEvent.class, listener);
    }

    public Registration addDropdownShownListener(
            ComponentEventListener<DropdownShownEvent> listener) {
        return addListener(DropdownShownEvent.class, listener);
    }

    @Override
    protected Div initContent() {
        div.addClassNames("uk-inline");
        div.add(button, dropdown);
        dropdown.getElement().addEventListener("hidden", event -> {
            fireEvent(new DropdownHiddenEvent(this, true));
        });
        dropdown.getElement().addEventListener("shown", event -> {
            fireEvent(new DropdownShownEvent(this, true));
        });
        return div;
    }

    public void setCaption(String caption) {
        button.setText(caption);
    }

    public void setButtonVariant(ButtonVariant variant) {
        button.setVariant(variant);
    }

    public void setIcon(UkIcon icon) {
        button.setIcon(icon);
    }

    @Override
    public void setFloat(FloatStyle floatStyle) {
        button.setFloat(floatStyle);
    }

    @Override
    public void setBorder(BorderStyle borderStyle) {
        button.setBorder(borderStyle);
    }

    public void removeAll() {
        dropdown.removeAll();
    }

    public void add(Component... components) {
        dropdown.add(components);
    }

    public void remove(Component... components) {
        dropdown.remove(components);
    }

    public void setDuration(Position position) {
        this.position = position;
        sendSettings();
    }

    public void setDuration(int duration) {
        this.duration = duration;
        sendSettings();
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
        sendSettings();
    }

    public void setClick(boolean click) {
        if (click) {
            mode = "click";
        } else {
            mode = "hover";
        }
        sendSettings();
    }

    public void show() {
        dropdown.getElement().executeJs("UIkit.dropdown($0).show()",
                dropdown.getElement());
    }

    public void hide() {
        dropdown.getElement().executeJs("UIkit.dropdown($0).hide()",
                dropdown.getElement());
    }

    public void hide(boolean delay) {
        dropdown.getElement().executeJs("UIkit.dropdown($0).hide($1)",
                dropdown.getElement(), delay);
    }

    private void sendSettings() {
        dropdown.getElement().executeJs(
                "UIkit.dropdown($0, {toggle: $1, pos: $2, mode: $3, delayShow: $4, flip: $5, offset: $6, animation: false, duration: $7})",
                dropdown.getElement(), "- *", position.getPosition(), mode, "0",
                delayHide, flip, "0", duration);
    }

    @SuppressWarnings("serial")
    public static class DropdownHiddenEvent extends ComponentEvent<UkDropdown> {

        public DropdownHiddenEvent(UkDropdown source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    @SuppressWarnings("serial")
    public static class DropdownShownEvent extends ComponentEvent<UkDropdown> {

        public DropdownShownEvent(UkDropdown source, boolean fromClient) {
            super(source, fromClient);
        }
    }

}

package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkBorder;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkAlert extends Composite<Div> implements HasSize, UkBorder {

    public enum AlertVariant {
        // @formatter:off
        SUCCESS("uk-alert-success"),
        PRIMARY("uk-alert-primary"),
        WARNING("uk-alert-warning"),
        DANGER("uk-alert-danger");
        // @formatter:on

        private final String variant;

        AlertVariant(String variant) {
            this.variant = variant;
        }

        /**
         * Gets the variant name.
         *
         * @return variant name
         */
        public String getVariantName() {
            return variant;
        }
    }

    private Div alert = new Div();
    private H3 titleComponent = new H3();
    private Paragraph p = new Paragraph();

    public UkAlert(String alertText) {
        this(alertText, null, 150, true);
    }

    public UkAlert(String alertText, AlertVariant variant) {
        this(alertText, variant, 150, true);
    }

    public UkAlert(String alertText, AlertVariant variant, int duration,
            boolean animation) {
        if (variant != null)
            alert.addClassName(variant.getVariantName());
        p.setText(alertText);
        sendSettings(duration, animation);
        getElement().addEventListener("hide", event -> {
            fireEvent(new AlertHiddenEvent(this, true));
        });
    }

    public Registration addAlertHiddenListener(
            ComponentEventListener<AlertHiddenEvent> listener) {
        return addListener(AlertHiddenEvent.class, listener);
    }

    private void sendSettings(int duration, boolean animation) {
        alert.getElement().executeJs(
                "UIkit.alert($0,{animation: $1, duration: $2, selClose: '.uk-alert-close'})",
                alert.getElement(), animation, duration);
    }

    @Override
    protected Div initContent() {
        alert.addClassNames("uk-alert");
        Anchor close = new Anchor();
        close.addClassName("uk-alert-close");
        close.getElement().setAttribute("uk-close", true);
        alert.add(close, titleComponent, p);
        return alert;
    }

    public void close() {
        alert.getElement().executeJs("UIkit.alert($0).close()",
                alert.getElement());
    }

    public static class AlertHiddenEvent extends ComponentEvent<UkAlert> {

        public AlertHiddenEvent(UkAlert source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}

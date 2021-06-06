package org.vaadin.uikit.components;

import org.vaadin.uikit.interfaces.UkMargin;
import org.vaadin.uikit.interfaces.UkPadding;
import org.vaadin.uikit.interfaces.UkTooltip;
import org.vaadin.uikit.interfaces.UkSizing;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;

public class UkLabel extends Composite<Span>
        implements UkTooltip, UkMargin, UkSizing, UkPadding {

    public enum LabelVariant {
        SUCCESS("uk-label-success"), 
        WARNING("uk-label-warning"), 
        DANGER("uk-label-danger");

        private final String variant;

        LabelVariant(String variant) {
            this.variant = variant;
        }

        public String getVariantName() {
            return variant;
        }
    }

    Span span = new Span();

    public UkLabel(String label) {
        span.setText(label);
    }

    public UkLabel(String label, LabelVariant variant) {
        span.setText(label);
        span.addClassName(variant.getVariantName());
    }

    @Override
    protected Span initContent() {
        span.addClassName("uk-label");
        return span;
    }
}
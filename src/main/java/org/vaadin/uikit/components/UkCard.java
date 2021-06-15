package org.vaadin.uikit.components;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkOverflow;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;

public class UkCard extends Composite<Div>
        implements HasSize, UkSizing, UkMargin, UkPadding, UkBorder, UkOverflow {

    H3 titleComponent = new H3();
    Div div = new Div();
    Component content = new Div();
    Div badge = new Div();

    public enum CardVariant {
        DEFAULT("uk-card-default"),
        PRIMARY("uk-card-primary"),
        SECONDARY("uk-card-secondary");

        private final String variant;

        CardVariant(String variant) {
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

    public UkCard() {
    }

    public UkCard(String title) {
        setTitle(title);
    }

    public UkCard(String title, Component component) {
        setTitle(title);
        setContent(component);
    }

    public UkCard(String title, String textContent) {
        setTitle(title);
        setText(textContent);
    }

    @Override
    protected Div initContent() {
        div.addClassNames("uk-card", "uk-card-default", "uk-card-body");
        titleComponent.addClassName("uk-card-title");
        badge.addClassNames("uk-card-badge", "uk-label");
        div.add(titleComponent, content);
        return div;
    }

    public void setVariant(CardVariant cardVariant) {
        for (CardVariant variant : CardVariant.values()) {
            div.removeClassName(variant.getVariantName());
        }
        div.addClassName(cardVariant.getVariantName());
    }

    public void setTitle(String title) {
        titleComponent.setText(title);
    }

    public void setText(String textContent) {
        div.setText(textContent);
    }

    public void setContent(String htmlContent) {
        String sanitized = Jsoup.clean(htmlContent, Whitelist.basic());
        if (content != null)
            div.remove(content);
        Html html = new Html(sanitized);
        div.add(html);
        content = html;
    }

    public void setContent(Component component) {
        content = component;
    }

    public void setBadge(String badgeText) {
        if (badgeText != null && !badgeText.isEmpty()) {
            badge.setText(badgeText);
            div.add(badge);
        } else {
            div.remove(badge);
        }
    }

    public void setHoverEffect(boolean hoverEffect) {
        if (hoverEffect) {
            div.addClassName("uk-card-hover");
        } else {
            div.removeClassName("uk-card-hover");
        }
    }
}

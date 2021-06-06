package org.vaadin.uikit.components;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.vaadin.uikit.interfaces.UkBorder;
import org.vaadin.uikit.interfaces.UkMargin;
import org.vaadin.uikit.interfaces.UkPadding;
import org.vaadin.uikit.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;

public class UkTile extends Composite<Div>
        implements HasSize, UkSizing, UkMargin, UkPadding, UkBorder {

    Div div = new Div();
    Component content = new Div();

    public enum TileVariant {
        DEFAULT("uk-tile-default"),
        MUTED("uk-tile-muted"),
        PRIMARY("uk-tile-primary"),
        SECONDARY("uk-tile-secondary");

        private final String variant;

        TileVariant(String variant) {
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

    public UkTile() {
    }

    public UkTile(String textContent) {
        setText(textContent);
    }

    public UkTile(Component component) {
        setContent(component);
    }

    @Override
    protected Div initContent() {
        div.addClassNames("uk-tile", "uk-tile-default");
        div.add(content);
        return div;
    }

    public void setVariant(TileVariant cardVariant) {
        for (TileVariant variant : TileVariant.values()) {
            div.removeClassName(variant.getVariantName());
        }
        div.addClassName(cardVariant.getVariantName());
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

}

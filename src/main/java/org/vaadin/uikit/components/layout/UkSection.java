package org.vaadin.uikit.components.layout;

import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.html.Div;

@SuppressWarnings("serial")
public class UkSection extends Div implements UkSizing, UkBorder {

    public enum SectionVariant {
        DEFAULT("uk-section-default"),
        MUTED("uk-section-muted"),
        PRIMARY("uk-section-primary"),
        SECONDARY("uk-section-secondary");

        private final String variant;

        SectionVariant(String variant) {
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

    public enum SectionPadding {
        XSMALL("uk-section-xsmall"),
        SMALL("uk-section-small"),
        LARGE("uk-section-large"),
        XLARGE("uk-section-xlarge"),
        NONE("uk-padding-remove-vertical");

        private final String padding;

        SectionPadding(String padding) {
            this.padding = padding;
        }

        public String getPadding() {
            return padding;
        }
    }

    public UkSection() {
        addClassName("uk-section");
    }

    public void setPadding(SectionPadding sectionPadding) {
        for (SectionPadding padding : SectionPadding.values()) {
            removeClassName(padding.getPadding());
        }
        addClassName(sectionPadding.getPadding());
    }

    public void setVariant(SectionVariant sectionVariant) {
        for (SectionVariant variant : SectionVariant.values()) {
            removeClassName(variant.getVariantName());
        }
        addClassName(sectionVariant.getVariantName());
    }
}

package org.vaadin.uikit.components.layout;

import com.vaadin.flow.component.html.Div;

public class UkContainer extends Div {

    public enum ContainerMaxWidth {
        XSMALL("uk-container-xsmall"),
        SMALL("uk-container-small"),
        LARGE("uk-container-large"),
        XLARGE("uk-container-xlarge"),
        EXPAND("uk-container-expand");

        private final String maxWidth;

        ContainerMaxWidth(String maxWidth) {
            this.maxWidth = maxWidth;
        }

        public String getMaxWidth() {
            return maxWidth;
        }
    }

    public UkContainer() {
        addClassName("uk-container");
    }

    public void setMaxWidth(ContainerMaxWidth maxWidth) {
        for (ContainerMaxWidth mx : ContainerMaxWidth.values()) {
            removeClassName(mx.getMaxWidth());
        }
        addClassName(maxWidth.getMaxWidth());
    }
}

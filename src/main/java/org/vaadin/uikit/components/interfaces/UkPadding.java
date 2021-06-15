package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkPadding extends HasElement {

    static String MODIFIER_BASE = "uk-padding-remove";

    public enum PaddingSize {
        DEFAULT("uk-padding"),
        SMALL("uk-padding-small"),
        LARGE("uk-padding-large");

        private final String padding;

        PaddingSize(String padding) {
            this.padding = padding;
        }

        public String getPadding() {
            return padding;
        }
    }

    public enum PaddingModifier {
        ALL(""),
        VERTICAL("-bottom"),
        HORIZONTAL("-left"),
        TOP("-top"),
        BOTTOM("-bottom"),
        LEFT("-left"),
        RIGHT("-right");

        private final String modifier;

        PaddingModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getModifier() {
            return modifier;
        }
    }

    default void setPadding() {
        getElement().getClassList().add(PaddingSize.DEFAULT.getPadding());
    }

    default void setPadding(PaddingSize paddingSize) {
        for (PaddingSize padding : PaddingSize.values()) {
            getElement().getClassList().remove(padding.getPadding());
        }
        getElement().getClassList().add(paddingSize.getPadding());
    }

    default void removePadding(PaddingModifier paddingModifier) {
        getElement().getClassList().forEach(padding -> {
            if (padding.startsWith("uk-padding-remove")) {
                getElement().getClassList().remove(padding);
            }
        });
        getElement().getClassList()
                .add(MODIFIER_BASE + paddingModifier.getModifier());
    }

    default void clearPadding() {
        getElement().getClassList().forEach(padding -> {
            if (padding.startsWith("uk-padding")) {
                getElement().getClassList().remove(padding);
            }
        });
    }
}

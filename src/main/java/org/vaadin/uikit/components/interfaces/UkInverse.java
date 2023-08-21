package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkInverse extends HasElement {

    public enum Invert {
        // @formatter:off
        NONE(null),
        LIGHT("uk-light"),
        DARK("uk-dark");
        // @formatter:on

        private final String invert;

        Invert(String invert) {
            this.invert = invert;
        }

        public String getInvert() {
            return invert;
        }
    }

    default void setInverse(Invert invert) {
        for (Invert inv : Invert.values()) {
            if (inv.getInvert() != null) {
                getElement().getClassList().remove(inv.getInvert());
            }
        }
        if (invert != Invert.NONE) {
            getElement().getClassList().add(invert.getInvert());
        }
    }
}
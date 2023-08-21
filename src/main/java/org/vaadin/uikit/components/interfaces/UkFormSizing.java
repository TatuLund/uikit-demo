package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkFormSizing extends HasElement {

    public enum FieldSize {
        // @formatter:off
        SMALL("uk-form-small"),
        MEDIUM(null),
        LARGE("uk-form-large");
        // @formatter:on

        private final String size;

        FieldSize(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }
    }

    public enum FieldWidth {
        // @formatter:off
        LARGE("uk-form-width-large"),
        MEDIUM("uk-form-width-medium"),
        SMALL("uk-form-width-small"),
        XSMALL("uk-form-width-xsmall");
        // @formatter:on

        private final String size;

        FieldWidth(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }
    }

    default void setSize(FieldSize size) {
        for (FieldSize s : FieldSize.values()) {
            if (s.getSize() != null)
                getElement().getClassList().remove(s.getSize());
        }
        if (size != FieldSize.MEDIUM)
            getElement().getClassList().add(size.getSize());
    }

    default void setWidth(FieldWidth width) {
        for (FieldSize w : FieldSize.values()) {
            getElement().getClassList().remove(w.getSize());
        }
        getElement().getClassList().add(width.getSize());
    }

}

package org.vaadin.uikit.components.layout;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKWidthAndHeight;

import com.vaadin.flow.component.html.Div;

public class UKFlex extends Div
        implements UKMargin, UKWidthAndHeight, UKPadding {

    public enum HorizontalAlignment {
        LEFT("uk-flex-left"),
        CENTER("uk-flex-center"),
        RIGHT("uk-flex-right"),
        BETWEEN("uk-flex-between"),
        AROUND("uk-flex-around");

        private final String alignment;

        HorizontalAlignment(String alignment) {
            this.alignment = alignment;
        }

        public String getAlignment() {
            return alignment;
        }
    }

    public enum VerticalAlignment {
        STRETCH("uk-flex-stretch"),
        TOP("uk-flex-top"),
        MIDDLE("uk-flex-middle"),
        BOTTOM("uk-flex-bottom");

        private final String alignment;

        VerticalAlignment(String alignment) {
            this.alignment = alignment;
        }

        public String getAlignment() {
            return alignment;
        }
    }

    public enum Direction {
        ROW("uk-flex-row"), ROW_REVERSE("uk-flex-row-reverse"), COLUMN(
                "uk-flex-column"), COLUMN_REVERSE("uk-flex-column-reverse");

        private final String direction;

        Direction(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return direction;
        }
    }

    public enum WrapBehavior {
        WRAP("uk-flex-wrap"), WRAP_REVERSE("uk-flex-wrap-reverse"), NOWRAP(
                "uk-flex-nowrap");

        private final String wrapBehavior;

        WrapBehavior(String wrapBehavior) {
            this.wrapBehavior = wrapBehavior;
        }

        public String getWrapBehavior() {
            return wrapBehavior;
        }
    }

    public enum WrapModifier {
        STRETCH("uk-flex-wrap-stretch"), BETWEEN(
                "uk-flex-wrap-between"), AROUND("uk-flex-wrap-around"), TOP(
                        "uk-flex-wrap-top"), MIDDLE(
                                "uk-flex-wrap-middle"), BOTTOM(
                                        "uk-flex-wrap-bottom");

        private final String wrapModifier;

        WrapModifier(String wrapModifier) {
            this.wrapModifier = wrapModifier;
        }

        public String getWrapModifier() {
            return wrapModifier;
        }
    }

    public UKFlex() {
        this(false);
    }

    public UKFlex(boolean inline) {
        if (inline) {
            addClassName("uk-flex");
        } else {
            addClassName("uk-flex-inline");
        }
    }

    public void setDirection(Direction direction) {
        for (Direction dir : Direction.values()) {
            removeClassName(dir.getDirection());
        }
        addClassName(direction.getDirection());
    }

    public void setHorizontalAlignment(HorizontalAlignment alignment) {
        for (HorizontalAlignment align : HorizontalAlignment.values()) {
            removeClassName(align.getAlignment());
        }
        addClassName(alignment.getAlignment());
    }

    public void setVerticalAlignment(VerticalAlignment alignment) {
        for (VerticalAlignment align : VerticalAlignment.values()) {
            removeClassName(align.getAlignment());
        }
        addClassName(alignment.getAlignment());
    }

    public void setWrapBehavior(WrapBehavior wrapBehavior) {
        for (WrapBehavior wrap : WrapBehavior.values()) {
            removeClassName(wrap.getWrapBehavior());
        }
        addClassName(wrapBehavior.getWrapBehavior());
        if (getClassNames().contains(WrapBehavior.NOWRAP.getWrapBehavior())) {
            for (WrapModifier wrap : WrapModifier.values()) {
                removeClassName(wrap.getWrapModifier());
            }
        }
    }

    public void setWrapModifier(WrapModifier wrapModifier) {
        if (!getClassNames().contains(WrapBehavior.WRAP.getWrapBehavior())
                && !getClassNames().contains(
                        WrapBehavior.WRAP_REVERSE.getWrapBehavior())) {
            if (getClassNames()
                    .contains(WrapBehavior.NOWRAP.getWrapBehavior())) {
                throw new IllegalStateException(
                        "Cannot set wrap modifier when using WrapBehavior.NOWRAP");
            } else {
                addClassName(WrapBehavior.WRAP.getWrapBehavior());
            }
        }
        for (WrapModifier wrap : WrapModifier.values()) {
            removeClassName(wrap.getWrapModifier());
        }
        addClassName(wrapModifier.getWrapModifier());
    }
}

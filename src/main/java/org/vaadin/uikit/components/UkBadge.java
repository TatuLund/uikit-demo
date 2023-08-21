package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkHidden;

import com.vaadin.flow.component.html.Span;

@SuppressWarnings("serial")
public class UkBadge extends Span implements UkHidden {

    public UkBadge() {
        addClassName("uk-badge");
    }
}

package org.vaadin.uikit.components.layout;

import org.vaadin.uikit.interfaces.UkSizing;

import com.vaadin.flow.component.html.Div;

public class UkPanel extends Div implements UkSizing {

    public UkPanel() {
        addClassName("uk-panel");
    }

    public void setScrollable(boolean scrollable) {
        if (scrollable) {
            addClassName("uk-panel-scrollable");
        } else {
            removeClassName("uk-panel-scrollable");            
        }
    }
}

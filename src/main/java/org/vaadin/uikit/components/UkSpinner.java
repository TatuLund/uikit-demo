package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkHidden;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;

@SuppressWarnings("serial")
public class UkSpinner extends Composite<Div> implements UkHidden {

    Div div = new Div();

    public UkSpinner() {
        div.getElement().setAttribute("uk-spinner", true);
    }

    public UkSpinner(double i) {
        div.getElement().setAttribute("uk-spinner", "ratio: " + i);
    }

    @Override
    protected Div initContent() {
        return div;
    }

}

package org.vaadin.uikit.components;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;

public class UKSpinner extends Composite<Div> {

    Div div = new Div();

    public UKSpinner() {
        div.getElement().setAttribute("uk-spinner",true);        
    }

    public UKSpinner(double i) {
        div.getElement().setAttribute("uk-spinner","ratio: "+i);        
    }

    @Override
    protected Div initContent() {
        return div;
    }

}

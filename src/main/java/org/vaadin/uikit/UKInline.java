package org.vaadin.uikit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;


public class UKInline extends Composite<Div> {
	Div div = new Div();

	public UKInline(UKIcons iconName, Component component) {
		Span icon = new Span();
		icon.addClassName("uk-form-icon");
		icon.getElement().setAttribute("uk-icon", iconName.getIconName());	
		div.setWidth("100%");
		div.add(icon, component);
	}

	@Override
	protected Div initContent() {
		div.addClassName("uk-inline");		
		return div;
	}
}

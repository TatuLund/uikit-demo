package org.vaadin.uikit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

public class UKInline extends Composite<Div> {
	Div div = new Div();
	Span icon = new Span();

	public UKInline(UKIcons iconName, Component component) {
		icon.addClassName("uk-form-icon");
		icon.getElement().setAttribute("uk-icon", iconName.getIconName());
		div.setWidth("100%");
		div.add(icon, component);
		component.getElement().addPropertyChangeListener("required", event -> {
			if (((HasValue) component).isRequiredIndicatorVisible()) {
				getElement().setProperty("required", true);
			} else {
				getElement().removeProperty("required");
			}
		});
	}

	public void setIconFlip(boolean iconFlip) {
		if (iconFlip) {
			icon.addClassName("uk-form-icon-flip");
		} else {
			icon.removeClassName("uk-form-icon-flip");
		}
	}

	@Override
	protected Div initContent() {
		div.addClassName("uk-inline");
		return div;
	}
}

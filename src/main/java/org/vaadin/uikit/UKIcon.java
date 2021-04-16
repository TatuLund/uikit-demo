package org.vaadin.uikit;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;

public class UKIcon extends Composite<Span> implements UKTooltip, UKMargin {
	Span icon = new Span();

	public UKIcon(UKIcons iconName) {
    	icon.addClassName("uk-margin-small-right");
    	icon.getElement().setAttribute("uk-icon", iconName.getIconName());	
	}

	public UKIcon(UKIcons iconName, int ratio) {
    	icon.addClassName("uk-margin-small-right");
    	icon.getElement().setAttribute("uk-icon", "icon: "+iconName.getIconName()+", ratio: "+ratio);	
	}

	public void setButtonStyle(boolean buttonStyle) {
		if (buttonStyle) {
			icon.addClassName("uk-icon-button");
		} else {
			icon.removeClassName("uk-icon-button");
		}		
	}
	
    @Override
	protected Span initContent() {
		return icon;
	}
}

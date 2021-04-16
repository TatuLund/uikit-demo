package org.vaadin.uikit;

import org.vaadin.uikit.UKCard.CardVariant;

import com.vaadin.flow.component.html.NativeButton;

public class UKButton extends NativeButton implements UKTooltip, UKMargin {

	public enum ButtonVariant {
		DEFAULT("uk-button-default"),
		PRIMARY("uk-button-primary"),
		SECONDARY("uk-button-secondary"),
		DANGER("uk-button-danger"),
		TEXT("uk-button-text"),
		LINK("uk-button-link");

	    private final String variant;

	    ButtonVariant(String variant) {
	        this.variant = variant;
	    }

	    public String getVariantName() {
	        return variant;
	    }
	}

	public UKButton(String caption) {
		super(caption);
		getElement().setAttribute("type", "button");
		addClassNames("uk-button","uk-button-default");
	}

	public void setVariant(ButtonVariant buttonVariant) {
		for (ButtonVariant variant : ButtonVariant.values()) { 
			removeClassName(variant.getVariantName());	
		}
		addClassName(buttonVariant.getVariantName());
	}
}

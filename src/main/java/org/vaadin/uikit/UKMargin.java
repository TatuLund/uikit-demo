package org.vaadin.uikit;

import com.vaadin.flow.component.HasElement;

public interface UKMargin extends HasElement {

	public enum MarginSize {
		DEFAULT("uk-margin"),
		SMALL("uk-margin-small"),
		MEDIUM("uk-margin-medium"),
		LARGE("uk-margin-large"),
		XLARGE("uk-margin-xlarge");

	    private final String margin;

	    MarginSize(String margin) {
	        this.margin = margin;
	    }

	    public String getMargin() {
	        return margin;
	    }
	}

	public enum MarginSide {
		TOP("-top"),
		BOTTOM("-bottom"),
		LEFT("-left"),
		RIGHT("-right");

	    private final String margin;

	    MarginSide(String margin) {
	        this.margin = margin;
	    }

	    public String getMarginSide() {
	        return margin;
	    }
	}

	default void setMargin() {
		getElement().getClassList().add(MarginSize.DEFAULT.getMargin());
	}
	
	default void setMargin(MarginSize margin) {
		getElement().getClassList().add(margin.getMargin());		
	}

	default void setMargin(MarginSize margin, MarginSide marginSide) {
		getElement().getClassList().add(margin.getMargin()+marginSide.getMarginSide());		
	}	

	default void clearMargin() {
		getElement().getClassList().forEach(height -> {
			if (height.startsWith("uk-margin")) {
				getElement().getClassList().remove(height);
			}
		});
	}
}

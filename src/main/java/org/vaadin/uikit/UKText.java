package org.vaadin.uikit;

import org.vaadin.uikit.UKWidthAndHeight.FixedWidth;

import com.vaadin.flow.component.HasElement;

public interface UKText extends HasElement {
	
	public enum TextAlignment {
		LEFT("uk-text-left"),
		RIGH("uk-text-right"),
		CENTER("uk-text-center"),
		JUSTIFY("uk-text-justify");

	    private String textAlignment;

	    TextAlignment(String textAlignment) {
	        this.textAlignment = textAlignment;
	    }

	    public String getAlignment() {
	        return textAlignment;
	    }
	}

	default void setTextAlignment(TextAlignment textAlignment) {
		for (TextAlignment alignment : TextAlignment.values()) {
			getElement().getClassList().remove(alignment.getAlignment());
		}
		getElement().getClassList().add(textAlignment.getAlignment());		
	}

	public enum TextVerticalAlignment {
		TOP("uk-text-top"),
		MIDDLE("uk-text-middle"),
		BOTTOM("uk-text-bottom"),
		BASELINE("uk-text-baseline");

	    private String textAlignment;

	    TextVerticalAlignment(String textAlignment) {
	        this.textAlignment = textAlignment;
	    }

	    public String getAlignment() {
	        return textAlignment;
	    }
	}

	default void setTextVerticalAlignment(TextVerticalAlignment textAlignment) {
		for (TextVerticalAlignment alignment : TextVerticalAlignment.values()) {
			getElement().getClassList().remove(alignment.getAlignment());
		}
		getElement().getClassList().add(textAlignment.getAlignment());		
	}	

	default void setTruncate(boolean truncate) {
		if (truncate) {
			getElement().getClassList().add("uk-text-truncate");
		} else {
			getElement().getClassList().remove("uk-text-truncate");			
		}
	}

	default void setBreakText(boolean breakText) {
		if (breakText) {
			getElement().getClassList().add("uk-text-break");
		} else {
			getElement().getClassList().remove("uk-text-break");			
		}
	}

	default void setNoWrap(boolean noWrap) {
		if (noWrap) {
			getElement().getClassList().add("uk-text-nowrap");
		} else {
			getElement().getClassList().remove("uk-text-nowrap");			
		}
	}
}

package org.vaadin.uikit;

import org.vaadin.uikit.UKText.FontSize;

import com.vaadin.flow.component.HasElement;

public interface UKFormSizing extends HasElement {

	public enum FieldSize {
		SMALL("uk-form-small"),
		DEFAULT(null),
		LARGE("uk-form-large");

	    private final String size;

	    FieldSize(String size) {
	        this.size = size;
	    }

	    public String getSize() {
	        return size;
	    }
	}

	public enum FieldWidth {
		LARGE("uk-form-width-large"),
		MEDIUM("uk-form-width-medium"),
		SMALL("uk-form-width-small"),
		XSMALL("uk-form-width-xsmall");

	    private final String size;

	    FieldWidth(String size) {
	        this.size = size;
	    }

	    public String getSize() {
	        return size;
	    }
	}

	default void setSize(FieldSize size) {
		for (FieldSize s : FieldSize.values()) {
			if (s.getSize() != null) getElement().getClassList().remove(s.getSize());
		}
		if (size != FieldSize.DEFAULT) getElement().getClassList().add(size.getSize());		
	}

	default void setWidth(FieldWidth width) {
		for (FieldSize w : FieldSize.values()) {
			getElement().getClassList().remove(w.getSize());
		}
		getElement().getClassList().add(width.getSize());		
	}

}

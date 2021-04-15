package org.vaadin.uikit;

import org.vaadin.uikit.UKButton.ButtonVariant;

import com.vaadin.flow.component.html.Div;

public class UKFlex extends Div {

	public enum HorizontalAlignment {
		LEFT("uk-flex-left"),
		CENTER("uk-flex-center"),
		RIGHT("uk-flex-right"),
		BETWEEN("uk-flex-between"),
		AROUND("uk-flex-around");

	    private final String alignment;

	    HorizontalAlignment(String alignment) {
	        this.alignment = alignment;
	    }

	    public String getAlignment() {
	        return alignment;
	    }		
	}

	public enum VerticalAlignment {
		STRETCH("uk-flex-stretch"),
		TOP("uk-flex-top"),
		MIDDLE("uk-flex-middle"),
		BOTTOM("uk-flex-bottom");

	    private final String alignment;

	    VerticalAlignment(String alignment) {
	        this.alignment = alignment;
	    }

	    public String getAlignment() {
	        return alignment;
	    }		
	}

	public enum Direction {
		ROW("uk-flex-row"),
		ROW_REVERSE("uk-flex-row-reverse"),
		COLUMN("uk-flex-column"),
		COLUMN_REVERSE("uk-flex-column-reverse");

	    private final String direction;

	    Direction(String direction) {
	        this.direction = direction;
	    }

	    public String getDirection() {
	        return direction;
	    }				
	}
	
	public UKFlex() {
		this(false);
	}

	public UKFlex(boolean inline) {
		if (inline) {
			addClassName("uk-flex");
		} else {
			addClassName("uk-flex-inline");
		}
	}

	public void setDirection(Direction direction) {
		for (Direction dir : Direction.values()) { 
			removeClassName(dir.getDirection());	
		}
		addClassName(direction.getDirection());
	}

	public void setHorizontalAlignment(HorizontalAlignment alignment) {
		for (HorizontalAlignment align : HorizontalAlignment.values()) { 
			removeClassName(align.getAlignment());	
		}
		addClassName(alignment.getAlignment());
	}

	public void setVerticalAlignment(VerticalAlignment alignment) {
		for (VerticalAlignment align : VerticalAlignment.values()) { 
			removeClassName(align.getAlignment());	
		}
		addClassName(alignment.getAlignment());
	}

}

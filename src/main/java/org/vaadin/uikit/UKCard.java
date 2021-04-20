package org.vaadin.uikit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;

public class UKCard extends Composite<Div> implements HasSize, UKWidthAndHeight, UKMargin {

	H3 titleComponent = new H3();
	Div div = new Div();
	Component content = new Div();
	Div badge = new Div();
	
	public enum CardVariant {
		DEFAULT("uk-card-default"),
		PRIMARY("uk-card-primary"),
		SECONDARY("uk-card-secondary");

	    private final String variant;

	    CardVariant(String variant) {
	        this.variant = variant;
	    }

	    /**
	     * Gets the variant name.
	     *
	     * @return variant name
	     */
	    public String getVariantName() {
	        return variant;
	    }
	}

	public UKCard() {
	}
	
	public UKCard(String title) {
		setTitle(title);
	}
	
	public UKCard(String title, Component component) {
		setTitle(title);
		setContent(component);
	}

	@Override
    protected Div initContent() {
    	div.addClassNames("uk-card","uk-card-default","uk-card-body");
    	titleComponent.addClassName("uk-card-title");
    	badge.addClassNames("uk-card-badge","uk-label");
    	div.add(titleComponent,content);
    	return div;
    }

	public void setVariant(CardVariant cardVariant) {
		for (CardVariant variant : CardVariant.values()) { 
			div.removeClassName(variant.getVariantName());	
		}
		div.addClassName(cardVariant.getVariantName());
	}

	public void setTitle(String title) {
		titleComponent.setText(title);
	}

	public void setContent(String htmlContent) {
		if (content != null) div.remove(content);
    	Html html = new Html(htmlContent);
    	div.add(html);
    	content = html;
	}

	public void setContent(Component component) {
		content = component;
	}

	public void setBadge(String badgeText) {
		if (badgeText != null && !badgeText.isEmpty()) {
			badge.setText(badgeText);
			div.add(badge);
		} else {
			div.remove(badge);
		}
	}

	public void setHoverEffect(boolean hoverEffect) {
		if (hoverEffect) {
			div.addClassName("uk-card-hover");
		} else {
			div.removeClassName("uk-card-hover");
		}
	}
}

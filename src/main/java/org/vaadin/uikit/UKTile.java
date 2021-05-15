package org.vaadin.uikit;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;

public class UKTile extends Composite<Div> implements HasSize, UKWidthAndHeight, UKMargin, UKPadding {

	Div div = new Div();
	Component content = new Div();
	
	public enum TileVariant {
		DEFAULT("uk-card-default"),
		MUTED("uk-card-muted"),
		PRIMARY("uk-card-primary"),
		SECONDARY("uk-card-secondary");

	    private final String variant;

	    TileVariant(String variant) {
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

	public UKTile() {
	}
	
	public UKTile(String htmlContent) {
		setContent(htmlContent);
	}
	
	public UKTile(Component component) {
		setContent(component);
	}

	@Override
    protected Div initContent() {
    	div.addClassNames("uk-tile","uk-card-default");
    	div.add(content);
    	return div;
    }

	public void setVariant(TileVariant cardVariant) {
		for (TileVariant variant : TileVariant.values()) { 
			div.removeClassName(variant.getVariantName());	
		}
		div.addClassName(cardVariant.getVariantName());
	}

	public void setContent(String htmlContent) {
		String sanitized = Jsoup.clean(htmlContent, Whitelist.basic());
		if (content != null) div.remove(content);
    	Html html = new Html(sanitized);
    	div.add(html);
    	content = html;
	}

	public void setContent(Component component) {
		content = component;
	}

}

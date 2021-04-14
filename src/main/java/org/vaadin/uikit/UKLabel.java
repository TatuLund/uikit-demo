package org.vaadin.uikit;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;

public class UKLabel extends Composite<Span> implements UKTooltip {

	public enum LabelVariant {
		SUCCESS("uk-label-success"), WARNING("uk-label-warning"),
		DANGER("uk-label-danger");

		private final String variant;

		LabelVariant(String variant) {
			this.variant = variant;
		}

		public String getVariantName() {
			return variant;
		}
	}

	Span span = new Span();

	public UKLabel(String label) {
		span.setText(label);
	}

	public UKLabel(String label, LabelVariant variant) {
		span.setText(label);
		span.addClassName(variant.getVariantName());
	}

	@Override
	protected Span initContent() {
		span.addClassName("uk-label");		
		return span;
	}
}

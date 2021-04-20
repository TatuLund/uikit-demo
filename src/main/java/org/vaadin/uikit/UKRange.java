package org.vaadin.uikit;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.data.value.ValueChangeMode;

@Tag(Tag.INPUT)
public class UKRange extends AbstractSinglePropertyField<UKRange, Double>
	implements UKValidation, HasStyle, Focusable<UKRange>, UKTooltip, UKFormSizing  {

    private static final PropertyDescriptor<String, String> typeDescriptor = PropertyDescriptors
            .attributeWithDefault("type", "range");	

    public UKRange() {
		this(0d,100d,1.0d);
	}	

	public UKRange(double min, double max, double step) {
		super("value", 0d, String.class, value -> Double.valueOf(value), value -> ""+value);
		addClassName("uk-range");
		getElement().setAttribute("type", "range");
		getElement().synchronizeProperty("value", "click");
		setMin(min);
		setMax(max);
		setStep(step);
	}

	public void setDisabled(boolean disabled) {
		this.getElement().setProperty("disabled", disabled);
	}

	public boolean isDisabled() {
		return getElement().getProperty("disabled", false);
	}
	
	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return super.isReadOnly();
	}

	public void setMin(double min) {
		getElement().setAttribute("min", ""+min);
	}
	
	public void setMax(double max) {
		getElement().setAttribute("max", ""+max);
	}
	
	public void setStep(double step) {
		getElement().setAttribute("step", ""+step);		
	}

}

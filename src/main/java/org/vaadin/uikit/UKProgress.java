package org.vaadin.uikit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;

@Tag("progress")
public class UKProgress extends Component implements UKWidthAndHeight, UKTooltip {

	int max = 100;

	public UKProgress() {
		getElement().getClassList().add("uk-progress");
		getElement().setAttribute("max", "" + max);
	}

	public void setValue(int value) {
		if (value > max)
			value = max;
		else if (value < 0)
			value = 0;
		getElement().setAttribute("value", "" + value);
	}
}

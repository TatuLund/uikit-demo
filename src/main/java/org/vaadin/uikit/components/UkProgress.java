package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkSizing;
import org.vaadin.uikit.components.interfaces.UkTooltip;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;

@Tag("progress")
public class UkProgress extends Component
        implements UkSizing, UkTooltip, UkMargin {

    int max = 100;

    public UkProgress() {
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

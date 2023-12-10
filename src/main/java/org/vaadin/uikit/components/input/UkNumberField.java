package org.vaadin.uikit.components.input;

@SuppressWarnings("serial")
public class UkNumberField
        extends NumberFieldBase<Double> {

    public UkNumberField() {
        this(0d, 100d, 1.0d);
    }

    public UkNumberField(double min, double max, double step) {
        super("value", 0d, String.class, value -> Double.valueOf(value),
                value -> "" + value);
        addClassName("uk-input");
        getElement().setAttribute("type", "number");
        setSynchronizedEvent("change");
        setMin(min);
        setMax(max);
        setStep(step);
    }
}

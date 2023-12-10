package org.vaadin.uikit.components.input;

import com.vaadin.flow.component.AttachEvent;

@SuppressWarnings("serial")
public class UkIntegerField extends NumberFieldBase<Integer> {

    public UkIntegerField() {
        this(0, 100, 1);
    }

    public UkIntegerField(int min, int max, int step) {
        super("value", 0, String.class, value -> toModel(value),
                value -> "" + value);
        addClassName("uk-input");
        getElement().setAttribute("type", "number");
        setSynchronizedEvent("change");
        setMin(min);
        setMax(max);
        setStep(step);
    }

    private static Integer toModel(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            // user can input "-" in the middle, which leads to format exception
        }
        return 0;
    }

    protected void onAttach(AttachEvent event) {
        // This prevents user to input "e", "." or "," which are normally
        // accepted in number input
        getElement().executeJs(
                "this.addEventListener('keydown', (e) => { if(!((/\\d/g).test(event.key) || ['-', 'Enter', 'Tab', 'ArrowRight', 'ArrowLeft', 'Home', 'End', 'Backspace', 'Delete'].includes(event.key))) event.preventDefault(); });",
                getElement());
    }
}

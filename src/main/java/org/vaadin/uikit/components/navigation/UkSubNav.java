package org.vaadin.uikit.components.navigation;

@SuppressWarnings("serial")
public class UkSubNav extends MenuBase {
    public UkSubNav() {
        setSub();
    }

    protected void setSub() {
        getElement().getClassList().add("uk-nav-sub");
    }
}
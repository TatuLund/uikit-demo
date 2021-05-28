package org.vaadin.uikit.navigation;

public class UKSubNav extends UKMenuBase {
    public UKSubNav() {
        setSub();
    }

    protected void setSub() {
        getElement().getClassList().add("uk-nav-sub");
    }
}
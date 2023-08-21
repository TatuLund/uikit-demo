package org.vaadin.uikit.components.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLink;

@SuppressWarnings("serial")
public class UkDropdownNav extends Composite<Div> {

    class DropdownNav extends MenuBase {
        DropdownNav() {
            setDropDown();
        }

        void setDropDown() {
            getElement().getClassList().add("uk-navbar-dropdown-nav");
        }

    }

    private Div wrapper = new Div();
    private DropdownNav nav = new DropdownNav();

    public UkNavItem addMenuItem(String text) {
        return nav.addMenuItem(text);
    }

    public UkNavItem addMenuItem(String text,
            Class<? extends Component> navigationTarget) {
        return nav.addMenuItem(text, navigationTarget);
    }

    public UkNavItem addMenuItem(String text, String href) {
        return nav.addMenuItem(text, href);
    }

    public UkNavItem addMenuItem(RouterLink link) {
        return nav.addMenuItem(link);
    }

    public UkNavItem addMenuItem(Anchor anchor) {
        return nav.addMenuItem(anchor);
    }

    public void addDivider() {
        nav.addDivider();
    }

    public void addHeader(String header) {
        nav.addHeader(header);
    }

    public void setPrimary() {
        nav.setPrimary();
    }

    @Override
    protected Div initContent() {
        wrapper.addClassName("uk-navbar-dropdown");
        wrapper.add(nav);
        return wrapper;
    }
}

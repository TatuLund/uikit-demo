package org.vaadin.uikit.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLink;

public class UKDropdownNav extends Composite<Div> {

    class DropdownNav extends UKMenuBase {
        DropdownNav() {
             setDropDown();
        }
        void setDropDown() {
             getElement().getClassList().add("uk-navbar-dropdown-nav");
        }

    }
    
    private Div wrapper = new Div();
    private DropdownNav nav = new DropdownNav();

    public UKNavItem addMenuItem(String text) {
        return nav.addMenuItem(text);
    }

    public UKNavItem addMenuItem(String text,
            Class<? extends Component> navigationTarget) {
        return nav.addMenuItem(text, navigationTarget);
    }

    public UKNavItem addMenuItem(String text, String href) {
        return nav.addMenuItem(text, href);
    }

    public UKNavItem addMenuItem(RouterLink link) {
        return nav.addMenuItem(link);
    }

    public UKNavItem addMenuItem(Anchor anchor) {
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


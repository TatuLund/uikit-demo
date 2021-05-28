package org.vaadin.uikit.navigation;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKWidthAndHeight;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;

@Tag(Tag.NAV)
public class UKNavbar extends HtmlComponent implements UKPadding, UKMargin, UKWidthAndHeight {

    private Div wrapper = new Div();
    private UnorderedList navbar = new UnorderedList();

    public enum Mode {
        HOVER, CLICK;
    }

    public enum Alignment {
        LEFT("uk-navbar-left"),
        CENTER("uk-navbar-center"),
        RIGHT("uk-navbar-right");

        private final String alignment;

        Alignment(String alignment) {
            this.alignment = alignment;
        }

        public String getAlignment() {
            return alignment;
        }
    }    


    public UKNavbarItem addNavbarItem(String text) {
        UKNavbarItem menuItem = new UKNavbarItem(text);
        navbar.add(menuItem);
        return menuItem;
    }

    public UKNavbarItem addNavbarItem(String text,
            Class<? extends Component> navigationTarget) {
        UKNavbarItem menuItem = new UKNavbarItem(text, navigationTarget);
        navbar.add(menuItem);
        return menuItem;
    }

    public UKNavbarItem addNavbarItem(String text, String href) {
        UKNavbarItem menuItem = new UKNavbarItem(text, href);
        navbar.add(menuItem);
        return menuItem;
    }

    public UKNavbarItem addNavbarItem(RouterLink link) {
        UKNavbarItem menuItem = new UKNavbarItem(link);
        navbar.add(menuItem);
        return menuItem;
    }

    public UKNavbarItem addNavbarItem(Anchor anchor) {
        UKNavbarItem menuItem = new UKNavbarItem(anchor);
        navbar.add(menuItem);
        return menuItem;
    }

    public void setTransparent(boolean transparent) {
        if (transparent) {
            addClassName("uk-navbar-transparent");            
        } else {
            removeClassName("uk-navbar-transparent");
        }
    }

    public UKNavbar() {
        this(Mode.HOVER);
    }

    public UKNavbar(Mode mode) {
        this(mode, Alignment.LEFT);
    }

    public UKNavbar(Mode mode, Alignment alignment) {
        if (mode == Mode.HOVER) {
            getElement().setAttribute("uk-navbar", true);
        } else {
            getElement().setAttribute("uk-navbar", "mode: click");            
        }
        addClassName("uk-navbar-container");
        wrapper.addClassName(alignment.getAlignment());
        navbar.addClassName("uk-navbar-nav");
        wrapper.add(navbar);
        this.getElement().appendChild(wrapper.getElement());
    }
}

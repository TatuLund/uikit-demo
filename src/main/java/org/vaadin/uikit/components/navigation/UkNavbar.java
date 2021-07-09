package org.vaadin.uikit.components.navigation;

import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;

@SuppressWarnings("serial")
@Tag(Tag.NAV)
public class UkNavbar extends HtmlComponent implements UkPadding, UkMargin, UkSizing {

    private Div left = new Div();
    private Div right = new Div();
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


    public UkNavbarItem addNavbarItem(String text) {
        UkNavbarItem menuItem = new UkNavbarItem(text);
        navbar.add(menuItem);
        return menuItem;
    }

    public UkNavbarItem addNavbarItem(String text,
            Class<? extends Component> navigationTarget) {
        UkNavbarItem menuItem = new UkNavbarItem(text, navigationTarget);
        navbar.add(menuItem);
        return menuItem;
    }

    public UkNavbarItem addNavbarItem(String text, String href) {
        UkNavbarItem menuItem = new UkNavbarItem(text, href);
        navbar.add(menuItem);
        return menuItem;
    }

    public UkNavbarItem addNavbarItem(RouterLink link) {
        UkNavbarItem menuItem = new UkNavbarItem(link);
        navbar.add(menuItem);
        return menuItem;
    }

    public UkNavbarItem addNavbarItem(Anchor anchor) {
        UkNavbarItem menuItem = new UkNavbarItem(anchor);
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

    public void setLogo(String logoText) {
        left.addClassName(Alignment.LEFT.getAlignment());
        Div item = new Div();
        item.addClassNames("uk-navbar-item","uk-logo");
        item.setText(logoText);
        left.removeAll();
        left.add(item);
        getElement().removeAllChildren();
        getElement().appendChild(left.getElement(),wrapper.getElement(),right.getElement());
    }

    public void addLogout(UkButton logout) {
        right.addClassName(Alignment.RIGHT.getAlignment());
        if (wrapper.getElement().getClassList().contains(Alignment.RIGHT.getAlignment())) {
            right.addClassName("uk-margin-small-left");
        }
        Div item = new Div();
        item.addClassName("uk-navbar-item");
        item.add(logout);
        right.removeAll();
        right.add(item);
        getElement().removeAllChildren();
        getElement().appendChild(left.getElement(),wrapper.getElement(),right.getElement());
    }

    public UkNavbar() {
        this(Mode.HOVER);
    }

    public UkNavbar(Mode mode) {
        this(mode, Alignment.LEFT);
    }

    public UkNavbar(Mode mode, Alignment alignment) {
        if (mode == Mode.HOVER) {
            getElement().setAttribute("uk-navbar", true);
        } else {
            getElement().setAttribute("uk-navbar", "mode: click");            
        }
        addClassName("uk-navbar-container");
        wrapper.addClassName(alignment.getAlignment());
        navbar.addClassName("uk-navbar-nav");
        wrapper.add(navbar);
        getElement().appendChild(wrapper.getElement());
    }
}

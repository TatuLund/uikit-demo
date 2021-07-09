package org.vaadin.uikit.components.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.router.RouterLink;

@SuppressWarnings("serial")
public class UkNavbarItem extends MenuItemBase {

    public UkNavbarItem(String text) {
        super(text);
    }

    public UkNavbarItem(String text,
            Class<? extends Component> navigationTarget) {
        super(text,navigationTarget);
    }

    public UkNavbarItem(String text, String href) {
        super(text,href);
    }

    public UkNavbarItem(Anchor anchor) {
        super(anchor);
    }

    public UkNavbarItem(RouterLink link) {
        super(link);
    }

    
    public UkDropdownNav addDropDown() {
        if (!subMenuPossible) {
            throw new IllegalStateException(
                    "Sub menu not possible in MenuItem having a link");
        }
        UkDropdownNav dropDown = new UkDropdownNav();
        li.addClassName("uk-parent");
        li.add(dropDown);
        return dropDown;
    }
}


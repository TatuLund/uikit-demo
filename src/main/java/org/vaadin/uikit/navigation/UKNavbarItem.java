package org.vaadin.uikit.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.router.RouterLink;

public class UKNavbarItem extends UKMenuItemBase {

    public UKNavbarItem(String text) {
        super(text);
    }

    public UKNavbarItem(String text,
            Class<? extends Component> navigationTarget) {
        super(text,navigationTarget);
    }

    public UKNavbarItem(String text, String href) {
        super(text,href);
    }

    public UKNavbarItem(Anchor anchor) {
        super(anchor);
    }

    public UKNavbarItem(RouterLink link) {
        super(link);
    }

    
    public UKDropdownNav addDropDown() {
        if (!subMenuPossible) {
            throw new IllegalStateException(
                    "Sub menu not possible in MenuItem having a link");
        }
        UKDropdownNav dropDown = new UKDropdownNav();
        li.addClassName("uk-parent");
        li.add(dropDown);
        return dropDown;
    }
}


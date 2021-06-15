package org.vaadin.uikit.components.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.router.RouterLink;

public class UkNavItem extends MenuItemBase {

    public UkNavItem(String text) {
        super(text);
    }

    public UkNavItem(String text,
            Class<? extends Component> navigationTarget) {
        super(text,navigationTarget);
    }

    public UkNavItem(String text, String href) {
        super(text,href);
    }

    public UkNavItem(Anchor anchor) {
        super(anchor);
    }

    public UkNavItem(RouterLink link) {
        super(link);
    }

    public UkSubNav addSubMenu() {
        if (!subMenuPossible) {
            throw new IllegalStateException(
                    "Sub menu not possible in MenuItem having a link");
        }
        UkSubNav subMenu = new UkSubNav();
        li.addClassName("uk-parent");
        li.add(subMenu);
        return subMenu;
    }
}
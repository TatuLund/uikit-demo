package org.vaadin.uikit.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.router.RouterLink;

public class UKNavItem extends UKMenuItemBase {

    public UKNavItem(String text) {
        super(text);
    }

    public UKNavItem(String text,
            Class<? extends Component> navigationTarget) {
        super(text,navigationTarget);
    }

    public UKNavItem(String text, String href) {
        super(text,href);
    }

    public UKNavItem(Anchor anchor) {
        super(anchor);
    }

    public UKNavItem(RouterLink link) {
        super(link);
    }

    public UKSubNav addSubMenu() {
        if (!subMenuPossible) {
            throw new IllegalStateException(
                    "Sub menu not possible in MenuItem having a link");
        }
        UKSubNav subMenu = new UKSubNav();
        li.addClassName("uk-parent");
        li.add(subMenu);
        return subMenu;
    }
}
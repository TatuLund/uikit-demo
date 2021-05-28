package org.vaadin.uikit.navigation;

import java.util.List;
import java.util.stream.Collectors;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKWidthAndHeight;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;

public abstract class UKMenuBase extends Composite<UnorderedList>
        implements UKWidthAndHeight, UKMargin, UKPadding {

    private UnorderedList ul = new UnorderedList();

    public void setAccordion(boolean multiple) {
        if (multiple) {
            ul.getElement().setAttribute("uk-nav", "multiple: true");
        } else {
            ul.getElement().setAttribute("uk-nav", true);
        }
        ul.getElement().getClassList().add("uk-nav-parent-icon");
    }

    public UKNavItem addMenuItem(String text) {
        UKNavItem menuItem = new UKNavItem(text);
        ul.add(menuItem);
        return menuItem;
    }

    public UKNavItem addMenuItem(String text,
            Class<? extends Component> navigationTarget) {
        UKNavItem menuItem = new UKNavItem(text, navigationTarget);
        ul.add(menuItem);
        return menuItem;
    }

    public UKNavItem addMenuItem(String text, String href) {
        UKNavItem menuItem = new UKNavItem(text, href);
        ul.add(menuItem);
        return menuItem;
    }

    public UKNavItem addMenuItem(RouterLink link) {
        UKNavItem menuItem = new UKNavItem(link);
        ul.add(menuItem);
        return menuItem;
    }

    public UKNavItem addMenuItem(Anchor anchor) {
        UKNavItem menuItem = new UKNavItem(anchor);
        ul.add(menuItem);
        return menuItem;
    }

    public void addDivider() {
        ListItem li = new ListItem();
        li.addClassName("uk-nav-divider");
        ul.add(li);
    }

    public void setDivider() {
        ul.addClassName("uk-nav-divider");
    }

    public void addHeader(String header) {
        ListItem li = new ListItem();
        li.setText(header);
        li.addClassName("uk-nav-header");
        ul.add(li);
    }

    public void setPrimary() {
        ul.removeClassName("uk-nav-default");
        ul.addClassName("uk-nav-primary");
    }

    public void toggle(UKNavItem item) {
        List<Component> items = ul.getChildren().collect(Collectors.toList());
        int index = items.indexOf(item) + 1;
        ul.getElement().executeJs("UIkit.nav($0).toggle($1)", ul.getElement(),
                index);
    }

    @Override
    protected UnorderedList initContent() {
        ul.addClassNames("uk-nav", "uk-nav-default");
        return ul;
    }
}

package org.vaadin.uikit.components.navigation;

import java.util.List;
import java.util.stream.Collectors;

import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;

@SuppressWarnings("serial")
public abstract class MenuBase extends Composite<UnorderedList>
        implements UkSizing, UkMargin, UkPadding {

    private UnorderedList ul = new UnorderedList();

    public void setAccordion(boolean multiple) {
        if (multiple) {
            ul.getElement().setAttribute("uk-nav", "multiple: true");
        } else {
            ul.getElement().setAttribute("uk-nav", true);
        }
        ul.getElement().getClassList().add("uk-nav-parent-icon");
    }

    public UkNavItem addMenuItem(String text) {
        UkNavItem menuItem = new UkNavItem(text);
        ul.add(menuItem);
        return menuItem;
    }

    public UkNavItem addMenuItem(String text,
            Class<? extends Component> navigationTarget) {
        UkNavItem menuItem = new UkNavItem(text, navigationTarget);
        ul.add(menuItem);
        return menuItem;
    }

    public UkNavItem addMenuItem(String text, String href) {
        UkNavItem menuItem = new UkNavItem(text, href);
        ul.add(menuItem);
        return menuItem;
    }

    public UkNavItem addMenuItem(RouterLink link) {
        UkNavItem menuItem = new UkNavItem(link);
        ul.add(menuItem);
        return menuItem;
    }

    public UkNavItem addMenuItem(Anchor anchor) {
        UkNavItem menuItem = new UkNavItem(anchor);
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

    public void toggle(UkNavItem item) {
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

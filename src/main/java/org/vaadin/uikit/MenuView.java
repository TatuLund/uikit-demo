package org.vaadin.uikit;

import org.vaadin.uikit.components.UKCard;
import org.vaadin.uikit.components.UKIcons;
import org.vaadin.uikit.components.UKNav;
import org.vaadin.uikit.components.UKNav.UKMenuItem;
import org.vaadin.uikit.components.UKNav.UKNavSub;
import org.vaadin.uikit.components.layout.UKFlex;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.router.Route;

@Route("menu")
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class MenuView extends UKFlex {

    public MenuView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setSizeFull();

        UKNav menu = new UKNav();
        menu.setWidth(FixedWidth.LARGE);
        menu.setPadding();
        menu.setPrimary();
        menu.addHeader("Menu");
        menu.addDivider();
        menu.addMenuItem("Main 1",MainView.class);
        UKMenuItem item1 = menu.addMenuItem("Main 2");
        item1.setIcon(UKIcons.MENU.create());
        item1.setTooltip("Toggle sub menu");
        menu.setAccordion(true);
        UKNavSub sub1 = item1.addSubMenu();
        sub1.setDivider();
        sub1.addMenuItem("Sub 1 - 1",AccordionView.class);
        sub1.addMenuItem("Sub 1 - 2",AccordionView.class);
        sub1.addMenuItem("Sub 1 - 3",AccordionView.class);
        UKMenuItem item2 = menu.addMenuItem("Main 3");
        item2.setIcon(UKIcons.GRID.create());
        UKNavSub sub2 = item2.addSubMenu();
        sub2.setDivider();
        sub2.setAccordion(false);
        sub2.addMenuItem("Sub 2 - 1",ComboView.class);
        sub2.addMenuItem("Sub 2 - 2",ComboView.class);
        UKMenuItem subItem = sub2.addMenuItem("Sub 2 - 3");
        UKNavSub subsub = subItem.addSubMenu();
        subsub.setDivider();
        subsub.addMenuItem("Subsub 1",TableView.class);
        subsub.addMenuItem("Subsub 2",TableView.class);
        menu.addMenuItem("Main 4",MainView.class);

        UKCard card = new UKCard("Menu");
        card.setContent(menu);
        add(card);
    }
}

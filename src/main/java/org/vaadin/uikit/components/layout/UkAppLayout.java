package org.vaadin.uikit.components.layout;

import java.util.List;

import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkOffCanvas;
import org.vaadin.uikit.components.UkButton.ButtonVariant;
import org.vaadin.uikit.components.UkIcon;
import org.vaadin.uikit.components.UkOffCanvas.AnimationMode;
import org.vaadin.uikit.components.layout.UkContainer.ContainerMaxWidth;
import org.vaadin.uikit.components.layout.UkFlex.Direction;
import org.vaadin.uikit.components.layout.UkFlex.VerticalAlignment;
import org.vaadin.uikit.components.layout.UkSection.SectionPadding;
import org.vaadin.uikit.components.layout.UkSection.SectionVariant;
import org.vaadin.uikit.interfaces.UkMargin.MarginSide;
import org.vaadin.uikit.interfaces.UkMargin.MarginSize;
import org.vaadin.uikit.navigation.UkDropdownNav;
import org.vaadin.uikit.navigation.UkNav;
import org.vaadin.uikit.navigation.UkNavbar;
import org.vaadin.uikit.navigation.UkNavbar.Alignment;
import org.vaadin.uikit.navigation.UkNavbar.Mode;
import org.vaadin.uikit.navigation.UkNavbarItem;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.server.RouteRegistry;
import com.vaadin.flow.server.SessionRouteRegistry;
import com.vaadin.flow.server.VaadinSession;

@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class UkAppLayout extends Composite<UkFlex> {

    UkFlex flex = new UkFlex();
    UkSection topBar;
    UkNav nav;
    UkNavbar navbar;
    UkOffCanvas offCanvas = new UkOffCanvas(AnimationMode.SLIDE);
    private UkContainer container = new UkContainer();
    private UkButton menu = new UkButton(UkIcons.MENU.create(1.5));
    private UkDropdownNav dropDown;
    private Div logo;
    private UkButton logout = new UkButton(UkIcons.LOCK.create(1.5));
    private String logoText;
    private boolean hasLogout;

    public enum MenuType {
        BAR, DROPDOWN, SIDE
    }

    public UkAppLayout() {
        logo = new Div();
        logo.addClassNames("uk-logo","uk-float-right");
        logout.setVariant(ButtonVariant.TEXT);
        logout.setMargin(MarginSize.SMALL,MarginSide.LEFT);
        menu.setVariant(ButtonVariant.TEXT);
        container.add(logo);
    }

    public UkNav getNav() {
        return nav;
    }

    public UkNavbar getNavbar() {
        return navbar;
    }

    public void setLogo(String logoText) {
        this.logoText = logoText;
        if (navbar != null) {
            navbar.setLogo(logoText);
        } else {
            logo.setText(logoText);
            if (hasLogout) logo.add(logout);
        }
    }

    public void setLogout() {
        hasLogout = true;
        if (navbar != null) {
            navbar.addLogout(logout);
        } else {
            if (logoText != null) logo.setText(logoText);
            logo.add(logout);
        }        
    }

    public void setMenu(String caption, MenuType type, boolean autoPopulate) {
        if (caption == null) caption = "";
        if (type == MenuType.BAR) {
            navbar = new UkNavbar(Mode.CLICK, Alignment.CENTER);
            navbar.setWidth(1,1);
            flex.add(navbar);
        } else if (type == MenuType.SIDE) {
            topBar = new UkSection();
            topBar.setVariant(SectionVariant.PRIMARY);
            container.add(menu);
            container.setMaxWidth(ContainerMaxWidth.EXPAND);
            topBar.setPadding(SectionPadding.XSMALL);
            topBar.setWidth(1, 1);
            topBar.add(container);
            nav = new UkNav();
            if (!caption.isEmpty()) {
                offCanvas.setTitle(caption);
            }
            offCanvas.setContent(nav);
            flex.add(offCanvas);
            menu.setVisible(true);
            menu.addClickListener(event -> {
                offCanvas.show();
            });
            flex.add(topBar);
        } else {
            navbar = new UkNavbar(Mode.HOVER, Alignment.RIGHT);
            UkNavbarItem item = getNavbar().addNavbarItem(caption);
            if (caption.isEmpty()) {
                UkIcon icon = UkIcons.MENU.create(1.5);
                icon.setButtonStyle(true);
                item.setIcon(icon);
            }
            dropDown = item.addDropDown();
            navbar.setWidth(1,1);
            flex.add(navbar);
        }

        if (autoPopulate) {
            if (type == MenuType.SIDE) {
                nav.setPrimary();
            } else if (type == MenuType.DROPDOWN) {
                dropDown.setPrimary();
            }                 
            populateMenu(type);
        }
        
    }

    private void populateMenu(MenuType type) {
        RouteRegistry reg = SessionRouteRegistry
                .getSessionRegistry(VaadinSession.getCurrent());
        List<RouteData> routes = reg.getRegisteredRoutes();
        routes.forEach(route -> {
            PageTitle title = route.getNavigationTarget()
                    .getAnnotation(PageTitle.class);
            String titleString = "";
            if (title != null) {
                titleString = title.value();
            } else {
                titleString = route.getNavigationTarget().getTypeName();
            }
            if (type == MenuType.SIDE) {
                getNav().addMenuItem(titleString, route.getNavigationTarget());
            } else if (type == MenuType.BAR) {
                getNavbar().addNavbarItem(titleString, route.getNavigationTarget());
            } else {
                dropDown.addMenuItem(titleString, route.getNavigationTarget());
            }            
        });
    }

    @Override
    protected UkFlex initContent() {        
        flex.setDirection(Direction.COLUMN);
        flex.setVerticalAlignment(VerticalAlignment.TOP);
        flex.setSizeFull();
        return flex;
    }

}

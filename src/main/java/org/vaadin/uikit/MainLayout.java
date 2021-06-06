package org.vaadin.uikit;

import org.vaadin.uikit.components.layout.UkAppLayout;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;

@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
public class MainLayout extends UkAppLayout implements RouterLayout {

    public MainLayout() {
        getNav().addMenuItem("Main",MainView.class);
        getNav().addMenuItem("Menu",MenuView.class);
        getNav().addMenuItem("Inputs",ComboView.class);
        getNav().addMenuItem("Table",TableView.class);
        getNav().addMenuItem("Accordion",AccordionView.class);
        getNav().addMenuItem("Description",DescriptionView.class);
        getNav().addMenuItem("Tab",TabView.class);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        getContent().getElement().appendChild(content.getElement());
    }
}

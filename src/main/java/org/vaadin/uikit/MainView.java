package org.vaadin.uikit;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class MainView extends Div {

    public MainView() {
    	getElement().getStyle().set("display","flex");
    	getElement().getStyle().set("flex-direction","column");
    	getElement().getStyle().set("align-items","center");
    	getElement().getStyle().set("justify-content","space-evenly");
    	setSizeFull();
    	
    	Div card = new Div();
    	card.setWidth("500px");
    	card.setHeight("200px");
    	card.addClassNames("uk-card","uk-card-default","uk-card-body","uk-width-1-2@m");
    	H3 h3 = new H3("Default");
    	h3.addClassName("uk-card-title");
    	Html html = new Html("<p>Lorem ipsum <a href=\"#\">dolor</a> sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>");
    	card.add(h3,html);
    	add(card);

    	UnorderedList breadcrumb = new UnorderedList();
    	breadcrumb.addClassName("uk-breadcrumb");
    	ListItem item1 = new ListItem();
    	item1.add(new Anchor("#","Home"));
    	ListItem item2 = new ListItem();
    	item2.add(new Anchor("#","Linked Category"));
    	ListItem item3 = new ListItem();
    	item3.addClassName("uk-disabled");
    	item3.add(new Anchor("#","Disabled Category"));
    	ListItem item4 = new ListItem();
    	item4.add(new Span("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
    	breadcrumb.add(item1,item2,item3,item4);
    	add(breadcrumb);
    	
    	Span icon = new Span();
    	icon.addClassName("uk-margin-small-right");
    	icon.getElement().setAttribute("uk-icon", "check");
    	add(icon);

    	NativeButton button = new NativeButton("Click me");
    	button.addClassNames("demo","uk-button","uk-button-default");
    	button.addClickListener(event -> {
    		getUI().ifPresent(ui -> ui.getPage().executeJs("UIkit.notification({message: $0})", "Notification message"));
    	});
    	add(button);

    	NativeButton openButton = new NativeButton("Open");
    	openButton.addClassNames("uk-button","uk-button-default","uk-margin-small-right");
    	openButton.getElement().setAttribute("type", "button");
    	openButton.getElement().setAttribute("uk-toggle", "target: #offcanvas-usage");
    	Anchor toggle = new Anchor("#offcanvas-usage","Open");
    	toggle.getElement().setAttribute("uk-toggle",true);
    	Div offcanvas = new Div();
    	offcanvas.setId("offcanvas-usage");
    	offcanvas.getElement().setAttribute("uk-offcanvas", true);
    	Div offcanvasBar = new Div();
    	offcanvasBar.addClassName("uk-offcanvas-bar");
    	NativeButton closeButton = new NativeButton();
    	closeButton.addClassName("uk-offcanvas-close");
    	closeButton.getElement().setAttribute("type", "button");
    	closeButton.getElement().setAttribute("uk-close", true);
    	H3 title = new H3("title");
    	Paragraph p = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
    	offcanvasBar.add(closeButton,title,p);
    	offcanvas.add(offcanvasBar);
    	add(offcanvas,openButton,toggle);
    	
    }
}

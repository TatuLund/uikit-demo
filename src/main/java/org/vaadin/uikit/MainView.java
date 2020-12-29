package org.vaadin.uikit;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
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
    	icon.getElement().setAttribute("uk-tooltip", "This is an icon");
    	add(icon);

    	NativeButton button = new NativeButton("Click me");
    	button.addClassNames("demo","uk-button","uk-button-default");
    	button.addClickListener(event -> {
    		getUI().ifPresent(ui -> ui.getPage().executeJs("UIkit.notification({message: $0})", "Notification message"));
    	});
    	add(button);

    	NativeButton openButton = new NativeButton("Open drawer");
    	openButton.addClassNames("uk-button","uk-button-default","uk-margin-small-right");
    	openButton.getElement().setAttribute("type", "button");
    	openButton.getElement().setAttribute("uk-toggle", "target: #offcanvas-usage");
    	Anchor toggle = new Anchor("#offcanvas-usage","Open drawer");
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
    	offcanvas.getElement().addEventListener("hide", event -> {
    		System.out.println("Drawer closed");
    	});
    	
    	Person person = new Person();
    	Div dialog = new Div();
    	dialog.setId("modal-sections");
    	dialog.getElement().setAttribute("uk-modal", true);
    	Div dialogContent = new Div();
    	dialogContent.addClassName("uk-modal-dialog");
    	NativeButton dialogClose = new NativeButton();
    	dialogClose.addClassName("uk-modal-close-fault");
    	dialogClose.getElement().setAttribute("type", "button");
    	dialogClose.getElement().setAttribute("uk-close", true);
    	Div header = new Div();
    	header.addClassName("uk-modal-header");
    	H2 dialogTitle = new H2("Person");
    	dialogTitle.addClassName("uk-modal-title");
    	header.add(dialogTitle);
    	Div form = new Div();
    	form.addClassName("uk-modal-body");
    	MyInput nameField = new MyInput();
    	nameField.addClassName("uk-input");
    	nameField.setPlaceholder("name");
    	MyInput ageField = new MyInput();
    	ageField.addClassName("uk-input");
    	ageField.setPlaceholder("age");
    	MyInput acceptField = new MyInput();
    	acceptField.addClassName("uk-checkbox");
    	acceptField.getElement().setAttribute("type", "checkbox");
    	MyInput storyField = new MyInput();
    	storyField.addClassName("uk-textarea");
    	storyField.setPlaceholder("story");
    	form.add(nameField,ageField,acceptField,storyField);
    	Div footer = new Div();
    	footer.addClassNames("uk-modal-footer","uk-text-right");
    	NativeButton cancelButton = new NativeButton("Cancel");
    	cancelButton.addClassNames("uk-button","uk-button-default","uk-modal-close");
    	NativeButton saveButton = new NativeButton("Save");
    	saveButton.addClassNames("uk-button","uk-button-primary");
    	footer.add(cancelButton,saveButton);
    	dialogContent.add(header,form,footer);
    	dialog.add(dialogContent);

    	Binder<Person> binder = new Binder<>();
    	binder.forField(nameField)
    		.asRequired("Required")
    		.withValidator(new StringLengthValidator("Too long",0,20))
    		.bind(Person::getName,Person::setName);
    	binder.forField(ageField)
    		.withConverter(new StringToIntegerConverter("Not a number"))
    		.withValidator(new IntegerRangeValidator("Minimum 18",18,null))
    		.bind(Person::getAge,Person::setAge);
    	binder.forField(acceptField).withConverter(new StringToBooleanConverter("Not a boolean")).bind(Person::isAccept,Person::setAccept);
    	binder.forField(storyField).bind(Person::getStory,Person::setStory);

    	NativeButton openDialog = new NativeButton("Edit");
    	openDialog.getElement().setAttribute("uk-toggle", "target: #modal-sections");
    	openDialog.addClassNames("uk-button","uk-button-default","uk-margin-small-right");
    	add(openDialog,dialog);
    	openDialog.addClickListener(event -> {
    		binder.readBean(person);
    	});
    	saveButton.addClickListener(event -> {
    		try {
				binder.writeBean(person);
	    		getUI().ifPresent(ui -> ui.getPage().executeJs("UIkit.notification({message: $0});UIkit.modal($1).hide()", "Saved: "+person.getName()+" "+person.getAge()+" "+person.getStory(),dialog.getElement()));
			} catch (ValidationException e) {
	    		getUI().ifPresent(ui -> ui.getPage().executeJs("UIkit.notification({message: $0, status: 'dange'})", "Person not valid"));
			}
    	});
    	dialog.getElement().addEventListener("hide", event -> {
    		System.out.println("Dialog closed");
    	});
    }
    
    public class MyInput extends Input implements HasValidation {

		private boolean invalid = false;
		private String errorMessage = "";

		@Override
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
			if (errorMessage != null && !errorMessage.isEmpty()) {
				getElement().setAttribute("uk-tooltip", errorMessage);
			} else {
				getElement().removeAttribute("uk-tooltip");
			}
		}

		@Override
		public String getErrorMessage() {
			return errorMessage;
		}

		@Override
		public void setInvalid(boolean invalid) {
			this.invalid = invalid;
			if (invalid) {
				addClassName("uk-form-danger");				
			} else {
				removeClassName("uk-form-danger");
				getElement().removeAttribute("uk-tooltip");
			}
			
		}

		@Override
		public boolean isInvalid() {
			return invalid;
		}
    	
    }
    
    public class Person {
    	private String name;
    	private int age;
    	private boolean accept;
    	private String story;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public boolean isAccept() {
			return accept;
		}
		public void setAccept(boolean accept) {
			this.accept = accept;
		}
		public String getStory() {
			return story;
		}
		public void setStory(String story) {
			this.story = story;
		}
    }
}

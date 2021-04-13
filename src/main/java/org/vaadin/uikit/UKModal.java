package org.vaadin.uikit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.NativeButton;

public class UKModal extends Composite<Div> {

	Div dialog = new Div();
	Div dialogContent = new Div();
	H2 dialogTitle = new H2("Person");
	Div footer = new Div();
	Div body = new Div();
	Div header = new Div();
	NativeButton dialogClose = new NativeButton();

	@Override
	protected Div initContent() {
    	dialog.setId("modal-sections");
    	dialog.getElement().setAttribute("uk-modal", true);
    	dialogContent.addClassName("uk-modal-dialog");
    	dialogClose.addClassName("uk-modal-close-fault");
    	dialogClose.getElement().setAttribute("type", "button");
    	dialogClose.getElement().setAttribute("uk-close", true);
    	header.addClassName("uk-modal-header");
    	dialogTitle.addClassName("uk-modal-title");
    	header.add(dialogTitle);
    	footer.addClassNames("uk-modal-footer","uk-text-right");
    	body.addClassName("uk-modal-body");
    	dialogContent.add(header,body,footer);
    	dialog.add(dialogContent);
    	return dialog;
	}	

	public void setId(String id) {
		dialog.setId(id);
	}

	public void setTitle(String title) {
		dialogTitle.setText(title);
	}

	public void add(Component... components) {
		body.add(components);
	}

	public void remove(Component... components) {
		body.add(components);
	}

	public void addToFooter(Component... components) {
		footer.add(components);
	}

	public void removeFromooter(Component... components) {
		footer.remove(components);
	}

	public void show() {
		dialog.getElement().executeJs("UIkit.modal($0).show()", dialog.getElement());
	}

	public void hide() {
		dialog.getElement().executeJs("UIkit.modal($0).hide()", dialog.getElement());
	}


}

package org.vaadin.uikit;

import org.vaadin.uikit.UKWidthAndHeight.FixedHeight;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.dom.Element;

public class UKModal extends Composite<Div> implements UKWidthAndHeight {

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
		footer.addClassNames("uk-modal-footer", "uk-text-right");
		body.addClassName("uk-modal-body");
		dialogContent.add(header, body, footer);
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

	@Override
	public void setWidth(FixedWidth width) {
		clearWidth();
		dialogContent.getElement().getClassList().add(width.getWidthName());
	}

	@Override
	public void setWidth(int share, int max) {
		clearWidth();	
		if (max % share == 0) {
			max = max / share;
			share = 1;
		}
		dialogContent.getElement().getClassList().add("uk-width-"+share+"-"+max);
	}
	
	@Override
	public void clearWidth() {
		dialogContent.getElement().getClassList().forEach(w -> {
			if (w.startsWith("uk-width")) {
				dialogContent.getElement().getClassList().remove(w);
			}
		});
	}

	@Override
	public void setHeight(FixedHeight height) {
		dialogContent.getElement().getClassList().add(height.getHeightName());		
	}

	@Override
	public void clearHeight() {
		dialogContent.getElement().getClassList().forEach(height -> {
			if (height.startsWith("uk-height")) {
				dialogContent.getElement().getClassList().remove(height);
			}
		});
	}
}

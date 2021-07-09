package org.vaadin.uikit.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkOffCanvas extends Composite<Div> {

    public enum AnimationMode {
        SLIDE("slide"),
        REVEAL("reveal"),
        PUSH("push"),
        NONE("none");

        private final String animationMode;

        AnimationMode(String animationMode) {
            this.animationMode = animationMode;
        }

        public String getAnimationMode() {
            return animationMode;
        }
    }

    private H3 titleComponent = new H3();
    private Div offcanvas = new Div();
    private Component content;
    private Div offcanvasBar = new Div();
    private NativeButton closeButton = new NativeButton();
    private AnimationMode animationMode = AnimationMode.SLIDE;
    private boolean escClose = true;
    private boolean bgClose = true;
    private boolean flip = false;
    private String container = "false";

    public UkOffCanvas() {
    }

    public Registration addOffCanvasHiddenListener(
            ComponentEventListener<OffCanvasHiddenEvent> listener) {
        return addListener(OffCanvasHiddenEvent.class, listener);
    }

    public Registration addOffCanvasShownListener(
            ComponentEventListener<OffCanvasShownEvent> listener) {
        return addListener(OffCanvasShownEvent.class, listener);
    }

    public UkOffCanvas(String title, AnimationMode animationMode) {
        this();
        this.animationMode = animationMode;
        titleComponent.setText(title);
    }

    public UkOffCanvas(AnimationMode animationMode) {
        this.animationMode = animationMode;
    }

    private void sendSettings() {
        offcanvas.getElement().executeJs(
                "UIkit.offcanvas($0, {escClose: $1, bgClose: $2, container: $3, flip: $4, mode: $5})",
                offcanvas.getElement(), escClose, bgClose, container, flip,
                animationMode.getAnimationMode());
    }

    public void setEscClose(boolean escClose) {
        this.escClose = escClose;
        sendSettings();
    }

    public void setBgClose(boolean bgClose) {
        this.bgClose = bgClose;
        sendSettings();
    }

    public void setContainer(String container) {
        if (container == null)
            container = "false";
        this.container = container;
        sendSettings();
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
        sendSettings();
    }

    public void show() {
        offcanvas.getElement().executeJs("UIkit.offcanvas($0).show()",
                offcanvas.getElement());
    }

    public void hide() {
        offcanvas.getElement().executeJs("UIkit.offcanvas($0).hide()",
                offcanvas.getElement());
    }

    public void setContent(String htmlContent) {
        if (content != null)
            offcanvasBar.remove(content);
        Html html = new Html(htmlContent);
        offcanvasBar.add(html);
        content = html;
    }

    public void setContent(Component component) {
        content = component;
    }

    public void setTitle(String title) {
        titleComponent.setText(title);
    }

    public void setId(String id) {
        offcanvas.setId(id);
    }

    public void setCloseButtonVisible(boolean visible) {
        closeButton.setVisible(visible);
    }

    public void setAnimationMode(AnimationMode animationMode) {
        this.animationMode = animationMode;
        sendSettings();
    }

    @Override
    protected Div initContent() {
        offcanvas.setId("offcanvas-usage");
        offcanvas.getElement().setAttribute("uk-offcanvas",
                "mode: " + animationMode.getAnimationMode());
        offcanvasBar.addClassName("uk-offcanvas-bar");
        closeButton.addClassName("uk-offcanvas-close");
        closeButton.getElement().setAttribute("type", "button");
        closeButton.getElement().setAttribute("uk-close", true);
        offcanvasBar.add(closeButton, titleComponent, content);
        offcanvas.add(offcanvasBar);
        offcanvas.getElement().addEventListener("hidden", event -> {
            fireEvent(new OffCanvasHiddenEvent(this, true));
        });
        offcanvas.getElement().addEventListener("shown", event -> {
            fireEvent(new OffCanvasShownEvent(this, true));
        });
        return offcanvas;
    }

    @SuppressWarnings("serial")
    public static class OffCanvasHiddenEvent
            extends ComponentEvent<UkOffCanvas> {

        public OffCanvasHiddenEvent(UkOffCanvas source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    @SuppressWarnings("serial")
    public static class OffCanvasShownEvent
            extends ComponentEvent<UkOffCanvas> {

        public OffCanvasShownEvent(UkOffCanvas source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}

package org.vaadin.uikit.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.vaadin.uikit.components.interfaces.UkInverse;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;
import org.vaadin.uikit.components.util.ClassResourceFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkSlideshow extends Composite<Div>
        implements UkSizing, UkMargin, UkPadding, UkInverse {

    private Div div = new Div();
    private UnorderedList ul = new UnorderedList();
    private UnorderedList dotNav = new UnorderedList();
    private Anchor left = new Anchor("#");
    private Anchor right = new Anchor("#");
    private List<UkSlide> slides = new ArrayList<>();
    private Animation animation = Animation.SLIDE;
    private boolean autoplay = false;
    private int autoplayInterval = 7000;
    private boolean finite = false;
    private boolean pauseOnHover = true;
    private String ratio = "16:9";

    public enum NavMode {
        DOTS, DOTS_INLINE, BUTTONS
    }

    public enum Animation {
        SLIDE("slide"),
        FADE("fade"),
        SCALE("scale"),
        PULL("pull"),
        PUSH("push");

        private final String animation;

        Animation(String status) {
            this.animation = status;
        }

        public String getAnimation() {
            return animation;
        }
    }

    public class UkSlide {
        ListItem li = new ListItem();
        UkImage image = new UkImage();
        Div div = null;

        public UkSlide(AbstractStreamResource streamResource) {
            image.setSrc(streamResource);
            image.getElement().setAttribute("uk-cover", true);
            li.add(image);
        }

        public void add(Component... components) {
            if (div == null) {
                div = new Div();
                div.addClassNames("uk-position-center", "uk-position-small");
                li.add(div);
            }
            div.add(components);
        }

        public void remove(Component... components) {
            div.remove(components);
        }

        protected ListItem getListItem() {
            return li;
        }
    }

    public UkSlideshow() {
        dotNav.setVisible(false);
    }

    public Registration addSlideShownListener(
            ComponentEventListener<SlideShownEvent> listener) {
        return addListener(SlideShownEvent.class, listener);
    }

    public UkSlide addSlide(String fileName) throws FileNotFoundException {
        StreamResource streamResource = null;
        streamResource = new StreamResource(fileName, new ClassResourceFactory(fileName));
        return addSlide(streamResource);
    }

    public UkSlide addSlide(AbstractStreamResource streamResource) {
        Objects.requireNonNull(streamResource,"Resource can't be null");
        UkSlide slide = new UkSlide(streamResource);
        ListItem li = slide.getListItem();
        li.getElement().addEventListener("itemshown", event -> {
            fireEvent(new SlideShownEvent(this, true, slide));
        });
        ul.add(li);
        slides.add(slide);
        return slide;
    }

    @Override
    public Div initContent() {
        div.getElement().setAttribute("uk-slideshow", true);
        div.addClassNames("uk-visible-toggle", "uk-position-relative");
        ul.addClassName("uk-slideshow-items");
        left.addClassNames("uk-position-center-left", "uk-position-small",
                "uk-hidden-hover");
        left.getElement().setAttribute("uk-slidenav-previous", true);
        left.getElement().setAttribute("uk-slideshow-item", "previous");
        right.addClassNames("uk-position-center-right", "uk-position-small",
                "uk-hidden-hover");
        right.getElement().setAttribute("uk-slidenav-next", true);
        right.getElement().setAttribute("uk-slideshow-item", "next");

        dotNav.addClassNames("uk-slideshow-nav", "uk-dotnav", "uk-flex-center",
                "uk-margin");
        div.add(ul, left, right, dotNav);
        return div;
    }

    public void show(UkSlide slide) {
        int index = slides.indexOf(slide);
        if (index != -1) {
            show(index);
        }
    }

    public void show(int index) {
        div.getElement().executeJs("UIkit.slideshow($0).show($1)",
                div.getElement(), index);
    }

    public void play() {
        div.getElement().executeJs("UIkit.slideshow($0).startAutoplay()",
                div.getElement());
    }

    public void stop() {
        div.getElement().executeJs("UIkit.slideshow($0).stopAutoplay()",
                div.getElement());
    }

    public UkSlide getSlide(int index) {
        return slides.get(index);
    }

    public void setNavMode(NavMode navMode) {
        if (navMode == NavMode.DOTS) {
            dotNav.setVisible(true);
            dotNav.addClassName("uk-flex-center");
            dotNav.removeClassName("uk-position-bottom-center");
            left.setVisible(false);
            right.setVisible(false);
        } else if (navMode == NavMode.DOTS_INLINE) {
            dotNav.setVisible(true);
            dotNav.removeClassName("uk-flex-center");
            dotNav.addClassName("uk-position-bottom-center");
            left.setVisible(false);
            right.setVisible(false);
        } else {
            dotNav.setVisible(false);
            left.setVisible(true);
            right.setVisible(true);
        }
    }

    public void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay;
        sendSettings();
    }

    public void setAutoplayInterval(int autoplayInterval) {
        if (autoplayInterval < 0) {
            setAutoplay(false);
        } else {
            this.autoplayInterval = autoplayInterval;
            this.autoplay = true;
            sendSettings();
        }
    }

    public void setFinite(boolean finite) {
        this.finite = finite;
        sendSettings();
    }

    public void setPauseOnHover(boolean pauseOnHover) {
        this.pauseOnHover = pauseOnHover;
        sendSettings();
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
        sendSettings();
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        sendSettings();
    }

    private void sendSettings() {
        div.getElement().executeJs(
                "UIkit.slideshow($0, {animation: $1, autoplay: $2, autoplayInterval: $3, draggable: true, easing: 'ease', finite: $4, pauseOnHover: $5, index: 0, velocity: 1, ratio: $6})",
                div.getElement(), animation.getAnimation(), autoplay,
                autoplayInterval, finite, pauseOnHover, ratio);
    }

    @SuppressWarnings("serial")
    public static class SlideShownEvent extends ComponentEvent<UkSlideshow> {

        private UkSlide slide;

        public SlideShownEvent(UkSlideshow source, boolean fromClient,
                UkSlide slide) {
            super(source, fromClient);
            this.slide = slide;
        }

        public UkSlide getSlide() {
            return slide;
        }
    }

}

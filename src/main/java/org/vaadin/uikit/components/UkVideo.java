package org.vaadin.uikit.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

import org.vaadin.uikit.components.interfaces.UkBoxShadow;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.util.FileRegistrations;
import org.vaadin.uikit.components.util.FileResourceFactory;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
@Tag("video")
public class UkVideo extends HtmlComponent
        implements UkHidden, UkBoxShadow, UkPadding, UkMargin {

    private static final PropertyDescriptor<String, String> srcDescriptor = PropertyDescriptors
            .attributeWithDefault("src", "");

    private static final PropertyDescriptor<String, Optional<String>> altDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("alt", "");

    private StreamRegistration registration;

    private StreamResource resource;

    /**
     * Creates a new empty video.
     */
    public UkVideo() {
        getElement().setAttribute("uk-video", "autoplay: inview");
        getElement().setAttribute("controls", true);
        getElement().setAttribute("preload", "auto");
        addDetachListener(event -> {
            FileRegistrations.unregisterResource(registration);
        });
        getElement().addEventListener("ended", event -> {
            fireEvent(new VideoEndedEvent(this, true));
        });
        getElement().addEventListener("pause", event -> {
            fireEvent(new VideoPausedEvent(this, true));
        });
        getElement().addEventListener("play", event -> {
            fireEvent(new VideoPlayEvent(this, true));
        });
    }

    /**
     * Creates an video with the given URL and an alternative text.
     *
     * @param src
     *            the video URL
     * @param alt
     *            the alternate text
     *
     * @see #setSrc(String)
     * @see #setAlt(String)
     */
    public UkVideo(String src, String alt) {
        this();
        setSrc(src);
        setAlt(alt);
    }

    /**
     * Creates an video with the given stream resource and an alternative text.
     *
     * @param src
     *            the resource value, not null
     * @param alt
     *            the alternate text
     * @throws FileNotFoundException
     *
     * @see #setSrc(AbstractStreamResource)
     * @see #setAlt(String)
     */
    public UkVideo(File file, String alt) throws FileNotFoundException {
        this();
        setSrc(file);
        setAlt(alt);
    }

    public Registration addVideoEndedListener(
            ComponentEventListener<VideoEndedEvent> listener) {
        return addListener(VideoEndedEvent.class, listener);
    }

    public Registration addVideoPausedListener(
            ComponentEventListener<VideoPausedEvent> listener) {
        return addListener(VideoPausedEvent.class, listener);
    }

    public Registration addVideoPlayListener(
            ComponentEventListener<VideoPlayEvent> listener) {
        return addListener(VideoPlayEvent.class, listener);
    }

    /**
     * Gets the video URL.
     *
     * @return the video URL
     */
    public String getSrc() {
        return get(srcDescriptor);
    }

    /**
     * Sets the video URL.
     *
     * @param src
     *            the video URL
     */
    public void setSrc(String src) {
        set(srcDescriptor, src);
    }

    /**
     * Sets the video URL with the URL of the given File. Range requests
     * supported.
     *
     * @param src
     *            the file, not null
     * @throws FileNotFoundException
     */
    public void setSrc(File file) throws FileNotFoundException {
        resource = new StreamResource(file.getName(),
                new FileResourceFactory(file));
        registration = FileRegistrations
                .registerResource(VaadinSession.getCurrent(), resource, file);
        getElement().setAttribute("src",
                registration.getResourceUri().toString());
    }

    /**
     * Set the video from StreamResource. Range requests not supported.
     * 
     * @param src
     *            The resource
     */
    public void setSrc(AbstractStreamResource src) {
        getElement().setAttribute("src", src);
    }

    /**
     * Sets the alternate text for the video.
     *
     * @param alt
     *            the alternate text
     */
    public void setAlt(String alt) {
        set(altDescriptor, alt);
    }

    /**
     * Gets the alternate text for the video.
     *
     * @return an optional alternate text, or an empty optional if no alternate
     *         text has been set
     */
    public Optional<String> getAlt() {
        return get(altDescriptor);
    }

    public void play() {
        getElement().executeJs("this.play()");
    }

    public void pause() {
        getElement().executeJs("this.pause()");
    }

    @SuppressWarnings("serial")
    public static class VideoPausedEvent extends ComponentEvent<UkVideo> {

        public VideoPausedEvent(UkVideo source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public static class VideoEndedEvent extends ComponentEvent<UkVideo> {

        public VideoEndedEvent(UkVideo source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public static class VideoPlayEvent extends ComponentEvent<UkVideo> {

        public VideoPlayEvent(UkVideo source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}

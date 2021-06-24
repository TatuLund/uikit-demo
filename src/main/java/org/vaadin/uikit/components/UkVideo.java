package org.vaadin.uikit.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.vaadin.uikit.components.UkAlert.AlertHiddenEvent;
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

@Tag("video")
public class UkVideo extends HtmlComponent {

    private static final PropertyDescriptor<String, String> srcDescriptor = PropertyDescriptors
            .attributeWithDefault("src", "");

    private static final PropertyDescriptor<String, Optional<String>> altDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("alt", "");

    private static final Map<String, AbstractMap.SimpleImmutableEntry<StreamRegistration, File>> resourcesRegistrations = Collections
            .synchronizedMap(new HashMap<>());

    private StreamRegistration registration;

    private File file;

    private StreamResource resource;

    /**
     * Creates a new empty video.
     */
    public UkVideo() {
        getElement().setAttribute("uk-video", "autoplay: inview");
        getElement().setAttribute("controls", true);
        getElement().setAttribute("preload", "auto");
        addDetachListener(event -> {
            unregisterResource(registration);
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

    public StreamRegistration registerResource(VaadinSession session, StreamResource streamResource,
            File file) {
        if (file == null || !file.exists() || streamResource == null)
            return null;

        StreamRegistration registration = session.getResourceRegistry()
                .registerResource(streamResource);

        getResourcesRegistrations().put(
                "/" + registration.getResourceUri().toString().replace(" ",
                        "%20"),
                new AbstractMap.SimpleImmutableEntry<>(registration, file));

        return registration;
    }

    public AbstractMap.SimpleImmutableEntry<StreamRegistration, File> unregisterResource(
            StreamRegistration registration) {
        if (registration == null)
            return null;

        registration.unregister();

        return getResourcesRegistrations()
                .remove(registration.getResourceUri().toString());
    }

    public void unregisterAllResources(StreamRegistration registration) {
        if (registration == null)
            return;

        getResourcesRegistrations()
                .forEach((key, value) -> value.getKey().unregister());
        getResourcesRegistrations().clear();
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
     * Sets the video URL with the URL of the given File.
     *
     * @param src
     *            the file, not null
     * @throws FileNotFoundException 
     */
    public void setSrc(File file) throws FileNotFoundException {
        this.file = file;
        this.resource  = new StreamResource(file.getName(), new FileResourceFactory(file));
        registration = registerResource(VaadinSession.getCurrent(),resource,file);
        getElement().setAttribute("src", registration.getResourceUri().toString());
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

    public static Map<String, AbstractMap.SimpleImmutableEntry<StreamRegistration, File>> getResourcesRegistrations() {
        return resourcesRegistrations;
    }
    
    public void play() {
        getElement().executeJs("this.play()");
    }

    public void pause() {
        getElement().executeJs("this.pause()");
    }

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

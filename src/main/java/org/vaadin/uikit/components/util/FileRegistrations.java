package org.vaadin.uikit.components.util;

import java.io.File;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

public class FileRegistrations {

    private static final Map<String, AbstractMap.SimpleImmutableEntry<StreamRegistration, File>> resourcesRegistrations = Collections
            .synchronizedMap(new HashMap<>());

    public static Map<String, AbstractMap.SimpleImmutableEntry<StreamRegistration, File>> getResourcesRegistrations() {
        return resourcesRegistrations;
    }

    public static StreamRegistration registerResource(VaadinSession session,
            StreamResource streamResource, File file) {
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

    public static AbstractMap.SimpleImmutableEntry<StreamRegistration, File> unregisterResource(
            StreamRegistration registration) {
        if (registration == null)
            return null;

        registration.unregister();

        return getResourcesRegistrations()
                .remove(registration.getResourceUri().toString());
    }

    public static void unregisterAllResources(StreamRegistration registration) {
        if (registration == null)
            return;

        getResourcesRegistrations()
                .forEach((key, value) -> value.getKey().unregister());
        getResourcesRegistrations().clear();
    }
}

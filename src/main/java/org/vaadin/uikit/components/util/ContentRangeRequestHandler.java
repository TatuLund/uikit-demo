package org.vaadin.uikit.components.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.communication.StreamRequestHandler;
import org.apache.http.HttpStatus;
import org.vaadin.uikit.components.UkVideo;

@SuppressWarnings("serial")
public class ContentRangeRequestHandler extends StreamRequestHandler {

    public static final int BUFFER_SIZE = 64 * 1024;

    @Override
    public boolean handleRequest(VaadinSession session, VaadinRequest request,
            VaadinResponse response) throws IOException {

        String header = request.getHeader("Range");
        if (header == null) {
            return super.handleRequest(session, request, response);
        }

        String splitter = "/";
        String resourcePathFromRequest = Arrays.stream(request.getPathInfo().split(splitter))        
                .map((String value) -> {
                    try {
                        return URLEncoder.encode(value, StandardCharsets.UTF_8.name()).replace("+", "%20");
                    } catch (UnsupportedEncodingException e1) {
                        return "";
                    }
                })
                .collect(Collectors.joining(splitter));
        
//        System.out.println("Path: "+resourcePathFromRequest);
//
        File file = UkVideo.getResourcesRegistrations()
                .get(resourcePathFromRequest).getValue();
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return true;
        }

        response.setHeader("Accept-Ranges", "bytes");

        FileInputStream input = new FileInputStream(file);

        if (session != null)
            session.access(() -> {
                long rangeStart;
                long rangeEnd = -1;

                String[] split = header.substring(6).split("-");
                rangeStart = Long.parseLong(split[0]);
                if (split.length == 2) {
                    rangeEnd = Long.parseLong(split[1]);
                }

                StreamResource streamResource = (StreamResource) UkVideo
                        .getResourcesRegistrations()
                        .get(resourcePathFromRequest).getKey().getResource();

                try {
                    if (streamResource != null)
                        writeResponse(session, response, file, input,
                                streamResource, rangeStart, rangeEnd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        return true;
    }

    public static void writeResponse(VaadinSession session,
            VaadinResponse response, File file, InputStream data,
            StreamResource streamResource, long rangeStart, long rangeEnd)
            throws IOException {

        if (data == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        OutputStream out = null;
        try {
            // Sets content type
            response.setContentType(Files.probeContentType(file.toPath()));

            // Sets cache headers
            if (streamResource != null) {
                response.setCacheTime(streamResource.getCacheTime());
            }

            final byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // Calculate range that is going to be served if this was range
            // request
            long contentLength = file.length();
            long bytesToWrite = -1;
            data.skip(rangeStart); // Skip to start offset of the request
            if (rangeStart >= 0 || rangeEnd > 0) {
                // 206 response code needed since this is partial data
                response.setStatus(HttpStatus.SC_PARTIAL_CONTENT);

                if (rangeEnd == -1)
                    rangeEnd = rangeStart + data.available() - 1;

                bytesToWrite = (rangeEnd + 1) - rangeStart;

                response.setHeader("Content-Range", "bytes " + rangeStart + "-"
                        + rangeEnd + "/" + contentLength);
                response.setHeader("Content-Length", "" + bytesToWrite);
            }
            out = response.getOutputStream();

            long totalWritten = 0;
            while ((bytesToWrite == -1 || totalWritten < bytesToWrite)
                    && (bytesRead = data.read(buffer)) > 0) {
                // Check if this was last part, hence possibly less
                if (bytesToWrite != -1) {
                    bytesRead = (int) Math.min(bytesRead,
                            bytesToWrite - totalWritten);
                }
                out.write(buffer, 0, bytesRead);

                totalWritten += bytesRead;
                if (totalWritten >= buffer.length) {
                    // Avoid chunked encoding for small resources
                    out.flush();
                }
            }
        } catch (EOFException ignore) {
            // Browser aborts when it notices that range requests are supported
            // Swallow e.g. Jetty
        } catch (IOException e) {
            String name = e.getClass().getName();
            if (name.equals(
                    "org.apache.catalina.connector.ClientAbortException")) {
                // Browser aborts when it notices that range requests are
                // supported
                // Swallow e.g. Tomcat
            } else {
                throw e;
            }
        } finally {
            tryToCloseStream(out);
            tryToCloseStream(data);
        }
    }

    public static void tryToCloseStream(InputStream in) {
        try {
            // try to close input stream (e.g. file handle)
            if (in != null) {
                in.close();
            }
        } catch (IOException e1) {
            // NOP
        }
    }

    public static void tryToCloseStream(OutputStream out) {
        try {
            // try to close output stream (e.g. file handle)
            if (out != null) {
                out.close();
            }
        } catch (IOException e1) {
            // NOP
        }
    }
}
package net.posco.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {

    public void setResponseHeader(HttpServletResponse response, String prefix, String contentType, String extension) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = prefix + timestamp + extension;
        response.setContentType(contentType);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"" + fileName + "\"";
        response.setHeader(headerKey, headerValue);
    }
}

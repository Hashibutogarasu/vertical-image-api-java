package git.hashibutogarasu.verticalimageapi;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class VerticalAPIServer implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        String startLine =
                t.getRequestMethod() + " " +
                        t.getRequestURI().toString() + " " +
                        t.getProtocol();
        System.out.println(startLine);

        Headers reqHeaders = t.getRequestHeaders();
        for (String name : reqHeaders.keySet()) {
            System.out.println(name + ": " + reqHeaders.getFirst(name));
        }

        InputStream is = t.getRequestBody();
        byte[] b = is.readAllBytes();
        is.close();
        String req = "";
        if (b.length != 0) {
            System.out.println();
            req = new String(b, StandardCharsets.UTF_8);
        }

        Headers resHeaders = t.getResponseHeaders();
        resHeaders.set("Content-Type", "application/json");
        resHeaders.set("Last-Modified",
                ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME));
        resHeaders.set("Server",
                "MyServer (" +
                        System.getProperty("java.vm.name") + " " +
                        System.getProperty("java.vm.vendor") + " " +
                        System.getProperty("java.vm.version") + ")");
        String resBody = "{}";
        try{
            resBody = new ImageGenerator(req).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        int statusCode = 200;
        long contentLength = resBody.getBytes(StandardCharsets.UTF_8).length;
        t.sendResponseHeaders(statusCode, contentLength);

        OutputStream os = t.getResponseBody();
        os.write(resBody.getBytes());
        os.close();
    }
}
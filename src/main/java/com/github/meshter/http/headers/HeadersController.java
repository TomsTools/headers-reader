package com.github.meshter.http.headers;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeadersController {
  private static Logger log = LoggerFactory.getLogger(HeadersController.class);

  @GetMapping("/headers")
  public Map<String, String> headers(HttpServletRequest request) {
    Enumeration<String> headers = request.getHeaderNames();
    Map<String, String> result = new HashMap<>();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintWriter pw = new PrintWriter(baos)) {
      pw.println(
          "Incoming request on interface " + request.getLocalAddr() + ":" + request.getLocalPort());
      pw.println("Client IP is: " + request.getRemoteAddr());
      pw.println("Headers are:");
      pw.println("***");
      headers.asIterator().forEachRemaining(header -> {
        pw.println("[" + header + "]=[" + request.getHeader(header) + "]");
        result.put(header, request.getHeader(header));
      });
      pw.println("***");
    }

    String output = new String(baos.toByteArray());

    log.debug(output);

    return result;
  }
}

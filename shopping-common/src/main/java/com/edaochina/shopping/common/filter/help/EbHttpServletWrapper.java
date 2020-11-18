package com.edaochina.shopping.common.filter.help;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class EbHttpServletWrapper extends HttpServletRequestWrapper {

    private String encoding = "UTF-8";
    private byte[] requestBodyIniBytes;
    private String body;

    public EbHttpServletWrapper(HttpServletRequest request) throws IOException {
        super(request);
        ServletInputStream stream = request.getInputStream();
        String requestBody = StreamUtils.copyToString(stream, Charset.forName(encoding));
        body = requestBody;
        requestBodyIniBytes = requestBody.getBytes(encoding);
    }

    public String getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream in;
        in = new ByteArrayInputStream(requestBodyIniBytes);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return in.read();
            }
        };
    }
}
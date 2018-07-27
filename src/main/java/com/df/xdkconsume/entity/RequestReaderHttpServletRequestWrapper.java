package com.df.xdkconsume.entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.df.xdkconsume.utils.HttpHelper;
/**
 * 包装类，解决request只能读取一次流的问题
 * @author 段帆
 * 2018年7月27日上午10:06:44
 */
public class RequestReaderHttpServletRequestWrapper extends HttpServletRequestWrapper  {
 
    private final byte[] body;
 
    public RequestReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }
 
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
 
    @Override
    public ServletInputStream getInputStream() throws IOException {
 
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
 
        return new ServletInputStream() {
 
            @Override
            public int read() throws IOException {
                return bais.read();
            }
 
            @Override
            public boolean isFinished() {
                return false;
            }
 
            @Override
            public boolean isReady() {
                return false;
            }
 
            @Override
            public void setReadListener(ReadListener readListener) {
 
            }
        };
    }
}

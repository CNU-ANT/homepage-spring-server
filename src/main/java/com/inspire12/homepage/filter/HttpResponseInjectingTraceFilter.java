package com.inspire12.homepage.filter;

import brave.Span;
import brave.Tracer;
import com.inspire12.homepage.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class HttpResponseInjectingTraceFilter extends GenericFilterBean {
    private final Tracer tracer;

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Span currentSpan = this.tracer.currentSpan();
        response.addHeader(Constant.TRACE_ID, currentSpan.context().traceIdString());
        chain.doFilter(request, response);
    }
}

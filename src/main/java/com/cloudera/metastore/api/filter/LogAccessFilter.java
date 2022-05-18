package com.cloudera.metastore.api.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
@Log
@RequiredArgsConstructor
public class LogAccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long elapsedTime = System.currentTimeMillis() - startTime;
            String user = request.getRemoteUser();
            String ip = request.getRemoteAddr();
            log.info(
                    "[" + user + "/" + ip + "] " +
                            request.getRequestURI() +
                            " (" + elapsedTime + "ms)");
        }
    }

}
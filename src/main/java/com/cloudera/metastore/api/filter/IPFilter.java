package com.cloudera.metastore.api.filter;

import com.cloudera.metastore.api.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Profile("ip-filter")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log
@RequiredArgsConstructor
public class IPFilter extends OncePerRequestFilter {

    private final AppConfig app;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private List<IpAddressMatcher> whiteList;

    @Override
    protected void initFilterBean() {
        if (whiteList == null) {
            whiteList = new ArrayList<>();
            File ipWhiteListFile = app.getAuth().getIpWhiteListFile();
            if (ipWhiteListFile.exists()) {
                log.info("Source IP filter enabled! Using whitelist file: " + ipWhiteListFile.getAbsoluteFile());
                try (BufferedReader reader = new BufferedReader(new FileReader(ipWhiteListFile))) {
                    String ip = reader.readLine();
                    while (ip != null) {
                        if (!ip.startsWith("#")) {
                            IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ip);
                            log.info("Restricting access from " + ip);
                            whiteList.add(ipAddressMatcher);
                        }
                        ip = reader.readLine();
                    }
                } catch (IOException e) {
                    log.severe("Error while reading from " + ipWhiteListFile.getAbsoluteFile());
                }
            } else {
                throw new RuntimeException("Whitelist file doesn't exists! Please specify app.auth.ipWhiteListFile" );
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        for (IpAddressMatcher ipAddressMatcher : whiteList) {
            if (ipAddressMatcher.matches(ip)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        log.info("Blocking request from " + ip);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
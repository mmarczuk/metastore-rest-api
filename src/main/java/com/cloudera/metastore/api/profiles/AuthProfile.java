package com.cloudera.metastore.api.profiles;

import com.cloudera.metastore.api.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Profile("authentication")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log
public class AuthProfile extends WebSecurityConfigurerAdapter {

    private final AppConfig app;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() throws RuntimeException {
        List<UserDetails> credentials = new ArrayList<>();

        File credentialsFile = app.getAuth().getCredentialsFile();
        if (!credentialsFile.exists()) {
            throw new RuntimeException("Credential file doesn't exists! Please specify app.auth.credentialsFile" );
        }

        Properties passwords = new Properties();
        try (FileReader credentialsReader = new FileReader(credentialsFile)) {
            log.info("Authentication Enabled! Using credentials at file: " + credentialsFile.getAbsoluteFile());
            passwords.load(credentialsReader);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read credential file: " + app.getAuth().getCredentialsFile(), e);
        }

        passwords.forEach((k, v) -> {
            String user = String.valueOf(k);
            String password = String.valueOf(v);
            credentials.add(createUserAndPassword(user, password));
        });
        return new InMemoryUserDetailsManager(credentials);
    }

    private UserDetails createUserAndPassword(String username, String password) {
        return User.withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles("USER")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

}

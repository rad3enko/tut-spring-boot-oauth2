package com.example;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LoginAspect {

    private static final String LOG_TEMPLATE = "Date: %s \nLogin: %s \nEmail: %s \nBio: %s \n\n";

    @Pointcut("execution(public * org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider.authenticate(..))")
    void loginEvent() { }

    @AfterReturning(value = "loginEvent()", returning = "result")
    void logAttempt(Object result) {
        Authentication authentication = (Authentication) result;
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        System.out.print(String.format(
                         LOG_TEMPLATE,
                         new Date(),
                         principal.getAttribute("login"),
                         principal.getAttribute("email"),
                         principal.getAttribute("bio")));
    }
}

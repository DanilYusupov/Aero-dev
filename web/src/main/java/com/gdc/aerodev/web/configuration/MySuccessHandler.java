package com.gdc.aerodev.web.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityConfig.UserWrapper user = (SecurityConfig.UserWrapper) authentication.getPrincipal();
        request.getSession().setAttribute("user", user.getId());
        response.sendRedirect("/user/" + String.valueOf(user.getId()));
    }
}

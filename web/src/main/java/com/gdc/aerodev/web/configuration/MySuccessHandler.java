package com.gdc.aerodev.web.configuration;

import com.gdc.aerodev.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySuccessHandler implements AuthenticationSuccessHandler {

    private final UserServiceImpl usr_service;

    MySuccessHandler(UserServiceImpl usr_service) {
        this.usr_service = usr_service;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityConfig.UserWrapper user = (SecurityConfig.UserWrapper) authentication.getPrincipal();
        request.getSession().setAttribute("client", usr_service.getUser(user.getId()));
        response.sendRedirect("/user/" + String.valueOf(user.getId()));
    }
}

package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.service.impl.UserService;
import com.gdc.aerodev.service.security.Hasher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {

    private final UserService service;

    public HomeController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/home")
    public String home() {
        return "home";
    }

    /**
     * Makes {@code User} registration by incoming params: <ul>
     *     <li> name </li>
     *     <li> password </li>
     *     <li> email </li>
     * </ul>
     * <p>
     *     Password encrypts before storing into database.
     * </p>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/home")
    public void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = service.createUser(
                request.getParameter("name"),
                Hasher.hash(request.getParameter("password")),
                request.getParameter("email"));
        if (id != null){
            response.getWriter().write(String.valueOf(id));
        } else {
            response.getWriter().write("null");
        }
    }

}

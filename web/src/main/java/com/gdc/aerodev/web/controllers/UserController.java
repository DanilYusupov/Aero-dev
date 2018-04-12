package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.service.impl.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public String user(Model model){
        return "user";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public ModelAndView getUser(@PathVariable String id){
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", service.getUser(Long.valueOf(id)));
        return mav;
    }
}

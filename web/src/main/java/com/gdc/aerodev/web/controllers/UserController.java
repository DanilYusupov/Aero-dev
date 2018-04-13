package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.service.postgres.PostgresUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    private final PostgresUserService service;

    public UserController(PostgresUserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public ModelAndView getUser(@PathVariable String id){
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", service.getDao().getById(Long.valueOf(id)));
        return mav;
    }
}

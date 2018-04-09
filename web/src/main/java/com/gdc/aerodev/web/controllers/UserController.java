package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.postgres.PostgresUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final PostgresUserService service;

    public UserController(PostgresUserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public String user(Model model){
        return "user";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public User getUser(@PathVariable String id){
        return service.getDao().getById(Long.valueOf(id));
    }
}

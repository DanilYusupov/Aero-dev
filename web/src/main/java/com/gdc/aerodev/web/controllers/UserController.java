package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.GenericProjectService;
import com.gdc.aerodev.service.GenericUserService;
import com.gdc.aerodev.service.impl.ProjectService;
import com.gdc.aerodev.service.impl.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    private final GenericUserService usr_service;
    private final GenericProjectService prj_service;

    public UserController(UserService usr_service, ProjectService prj_service) {
        this.usr_service = usr_service;
        this.prj_service = prj_service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public ModelAndView getUser(@PathVariable String id){
        User user = usr_service.getUser(Long.valueOf(id));
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", user);
        mav.addObject("prjs", prj_service.getByUserId(user.getUserId()));
        return mav;
    }
}

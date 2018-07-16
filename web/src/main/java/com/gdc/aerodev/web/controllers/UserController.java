package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.OfferService;
import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.UserService;
import com.gdc.aerodev.service.impl.ProjectServiceImpl;
import com.gdc.aerodev.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    private final UserService usr_service;
    private final ProjectService prj_service;
    private final OfferService off_service;

    public UserController(UserService usr_service, ProjectService prj_service, OfferService off_service) {
        this.usr_service = usr_service;
        this.prj_service = prj_service;
        this.off_service = off_service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/{id}")
    public ModelAndView getUser(@PathVariable String id){
        User user = usr_service.getUser(Long.valueOf(id));
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", user);
        mav.addObject("prjs", prj_service.getByUserId(user.getUserId()));
        mav.addObject("offs", off_service.getByUserId(Long.valueOf(id)));
        return mav;
    }
}

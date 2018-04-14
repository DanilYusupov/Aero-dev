package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.impl.ProjectService;
import com.gdc.aerodev.service.impl.UserService;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class    ProfileController implements LoggingWeb{

    private final UserService usrService;
    private final ProjectService prjService;

    public ProfileController(UserService usrService, ProjectService prjService) {
        this.usrService = usrService;
        this.prjService = prjService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/profile")
    public ModelAndView profile(HttpSession session){
        try {
            User user = usrService.getUser((Long) session.getAttribute("user"));
            log.debug("Received user '" + user.getUserName() + "'.");
            ModelAndView mav = new ModelAndView("profile");
            mav.addObject("user", user);
            mav.addObject("prjs", prjService.getByUserId(user.getUserId()));
            return mav;
        } catch (NullPointerException e){
            log.error("Cannot receive users id as session attribute.");
            return new ModelAndView("/login");
        }
    }

}

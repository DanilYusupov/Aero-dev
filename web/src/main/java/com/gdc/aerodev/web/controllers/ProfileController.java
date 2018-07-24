package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.OfferService;
import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.UserService;
import com.gdc.aerodev.service.impl.ProjectServiceImpl;
import com.gdc.aerodev.service.impl.UserServiceImpl;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController implements LoggingWeb {

    private final UserService usrService;
    private final ProjectService prjService;
    private final OfferService offService;

    public ProfileController(UserService usrService, ProjectService prjService, OfferService offService) {
        this.usrService = usrService;
        this.prjService = prjService;
        this.offService = offService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/profile")
    public ModelAndView profile(HttpSession session) {
        User user = (User) session.getAttribute("client");
        log.debug("Received user '" + user.getUserName() + "'.");
        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", user);
        mav.addObject("prjs", prjService.getByUserId(user));
        mav.addObject("offs", offService.getByUserId(user.getUserId()));
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/profile/update")
    public String updateInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("client");
        usrService.updateInfo(
                user.getUserId(),
                request.getParameter("first_name"),
                request.getParameter("last_name"),
                request.getParameter("biography"),
                request.getParameter("country"),
                request.getParameter("city")
        );
        return "redirect:/profile";
    }
}
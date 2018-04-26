package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.ProjectContentService;
import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.impl.ProjectServiceImpl;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CreateProjectController implements LoggingWeb {

    private final ProjectService prjService;
    private final ProjectContentService contentService;

    public CreateProjectController(ProjectService prjService, ProjectContentService contentService) {
        this.prjService = prjService;
        this.contentService = contentService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/create_prj")
    public ModelAndView getPage(HttpSession session) {
        User user = (User) session.getAttribute("client");
        log.debug("Received user '" + user.getUserName() + "'.");
        ModelAndView mav = new ModelAndView("create_prj");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create_prj")
    public String createProject(HttpServletRequest request) {
        Long owner = Long.valueOf(request.getParameter("usrId"));
        String description = request.getParameter("description");
        ProjectType type = ProjectType.valueOf(request.getParameter("type").toUpperCase());
        log.debug("Received params from request:\n" + "User id = " + owner + "\nDescription: " + description + "\nType: " + type);
        Long id = prjService.createProject(request.getParameter("name"),
                owner,
                type);
        if (id != null) {
            contentService.createProjectContent(id,
                    new byte[0],
                    description,
                    new Date());
            log.info("Created project with id " + id + ". Owner id " + owner + ".");
            return "redirect:/profile";
        } else {
            log.error("Cannot create project for user (id " + owner + ").");
            return "redirect:/create_prj?error";
        }
    }

}

package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.ProjectContentService;
import com.gdc.aerodev.service.ProjectImageService;
import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.UserService;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ProjectController implements LoggingWeb{

    private final ProjectService prj_service;
    private final ProjectContentService contentService;
    private final ProjectImageService imageService;
    private final UserService usr_service;

    public ProjectController(ProjectService prj_service, ProjectContentService contentService, ProjectImageService imageService, UserService usr_service) {
        this.prj_service = prj_service;
        this.contentService = contentService;
        this.imageService = imageService;
        this.usr_service = usr_service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/project/{id}")
    public ModelAndView project(@PathVariable Long id, HttpSession session){
        ModelAndView mav = new ModelAndView("project");
        Project project = prj_service.getProject(id);
        User user = (User) session.getAttribute("client");
        log.debug("Received project '" + project.getProjectName() + "'.");
        mav.addObject("prj", project);
        mav.addObject("isOwner", prj_service.isOwner(project, user.getUserId()));
        mav.addObject("ownerName", usr_service.getUser(project.getProjectOwner()).getUserName());
        mav.addObject("content", contentService.get(id));
        mav.addObject("images", imageService.getAll(id));
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/project/logo/{projectId}")
    public ResponseEntity<byte[]> getLogo(@PathVariable Long projectId){
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(contentService.get(projectId).getProjectLogo(), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/project/image/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId){
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(imageService.get(imageId).getProjectImage(), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/project/logo")
    public String setLogo(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Long projectId = Long.valueOf(request.getParameter("project-id"));
        log.debug("Received project id: '" + projectId + "' for logo uploading.");
        contentService.updateProjectContent(projectId, file.getBytes(), "");
        return "redirect:/project/" + projectId;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/project/description")
    public String changeDescription(HttpServletRequest request){
        Long projectId = Long.valueOf(request.getParameter("project-id"));
        log.debug("Received project id: '" + projectId + "' for description changing.");
        String description = request.getParameter("description");
        log.debug("New description is: '" + description + "'.");
        contentService.updateProjectContent(projectId, new byte[0], description);
        return "redirect:/project/" + projectId;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/project/image")
    public String uploadImage(@RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        Long projectId = Long.valueOf(request.getParameter("project-id"));
        log.debug("Received project id: '" + projectId + "'for image uploading.");
        Long id = imageService.createImage(projectId, file.getBytes(), file.getContentType());
        log.info("Saved new image with id: " + id + " for project with id: " + projectId + ".");
        return "redirect:/project/" + projectId;
    }
}

package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.service.ProjectService;
import com.gdc.aerodev.service.UserService;
import com.gdc.aerodev.service.security.Hasher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import static com.gdc.aerodev.external.spacex.LastLaunchGetter.getData;

@Controller
public class HomeController {

    private final UserService usr_service;
    private final ProjectService prj_service;
    private final String spaceXUrl = "https://api.spacexdata.com/v2/launches/latest";

    public HomeController(UserService usr_service, ProjectService prj_service) {
        this.usr_service = usr_service;
        this.prj_service = prj_service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/home")
    public ModelAndView home() throws IOException {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("top_users", usr_service.getTopThree());
        //FIXME: realize author name translating
        mav.addObject("top_prj", prj_service.getTopThree());
        mav.addObject("spaceX", getData(getSpaceXJson()));
        return mav;
    }

    /**
     * Makes {@code User} registration by incoming params: <ul>
     * <li> name </li>
     * <li> password </li>
     * <li> email </li>
     * </ul>
     * <p>
     * Password encrypts before storing into database.
     * </p>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/home")
    public String signUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = usr_service.createUser(
                request.getParameter("name"),
                Hasher.hash(request.getParameter("password")),
                request.getParameter("email"),
                Boolean.parseBoolean(request.getParameter("male"))
        );
        if (id != null) {
            return "redirect:/user/" + id;
        } else {
            return "redirect:/home?error";
        }
    }

    private JsonObject getSpaceXJson() throws IOException {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = template.exchange(spaceXUrl, HttpMethod.GET, entity, String.class);
        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

}

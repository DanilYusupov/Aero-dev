package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.postgres.PostgresUserService;
import com.gdc.aerodev.service.security.Hasher;
import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController implements LoggingWeb{

    private final PostgresUserService service;

    public LoginController(PostgresUserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    public String getLogin(){
        return "login";
    }

    /**
     * Sets {@code User} as session attribute 'user' if login success. Or redirects to '/home?error'
     * @param request HTTP request from client
     * @return (0) '/user/{id}' redirection if login success or <br>
     *         (1) '/home?error' redirection if access denied
     */
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public String login(HttpServletRequest request){
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        log.info("Received (name/password): (" + name + "/" + password + ")");
        if (!service.isExistent(name)){
            log.error("No such user '" + name + "'");
            return "redirect:/nouser";
        }
        User user = service.getDao().getByName(name);
        String hashed = Hasher.hash(password);
        if (user.getUserPassword().matches(hashed)){
            request.getSession().setAttribute("user", user);
            return "redirect:/user/" + String.valueOf(user.getUserId());
        } else {
            log.error("Password: '" + hashed + "' for '" + name + "' with id " + user.getUserId() +
                    " doesn't matches. " + user.getUserPassword());
            return "redirect:/invalidpass";
        }
    }
}

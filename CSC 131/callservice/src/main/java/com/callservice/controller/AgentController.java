package com.callservice.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.callservice.entity.AgentEntity;
import com.callservice.entity.User;
import com.callservice.service.AgentService;
import com.callservice.service.UserService;
import com.callservice.utils.StatisticsHelper;
import com.callservice.utils.ValidatorHelper;



/**
 * Controller to handle rendering web pages using Thymeleaf; essentially though pure html css and js.
 */
@Controller
public class AgentController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    private AgentService entityService;

    public AgentController() {}

    //only allow get method in case of address bar url invocation and post method so front can securely send data
    //any other methods should not be allowed to this route so server will automatically return error page in such case
    @RequestMapping(value = {"/landing"}, method = {RequestMethod.GET})
    public String ahoy() 
    {
        return "landing";
    }

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = { "/home"}, method = {RequestMethod.GET})
    public String hello(Model model,
            @RequestParam(name = "status", required = false) String filter) 
    {

        List<AgentEntity> agents;
        filter = filter != null ? (filter.equalsIgnoreCase("loggedout") ? "logged-out" : filter)
                : null;

        
        if (filter != null && ValidatorHelper.validFilter(filter)) {
            agents = entityService.filterEntities(filter);
        } else {
            agents = entityService.getEntities();
        }
        StatisticsHelper entityInformation = new StatisticsHelper(agents);


        model.addAttribute("agents", agents);
        model.addAttribute("available", entityInformation.getAllAvailable());
        model.addAttribute("busy", entityInformation.getAllBusy());
        model.addAttribute("preview", entityInformation.getAllPreview());
        model.addAttribute("after", entityInformation.getAllAfter());
        model.addAttribute("loggedout", entityInformation.getAllLoggedOut());

        logger.info("Page has agents " + agents.size() + " agents");
        logger.info("Returning index page");
        return "index";
    }

    @RequestMapping(value = "/stats", method = {RequestMethod.GET})
    public String settingsPage(Model model) {
        List<AgentEntity> agents = entityService.getEntities();
        StatisticsHelper entityInformation = new StatisticsHelper(agents);
        
        model.addAttribute("agents", agents);
        model.addAttribute("available", entityInformation.getAllAvailable());
        model.addAttribute("busy", entityInformation.getAllBusy());
        model.addAttribute("preview", entityInformation.getAllPreview());
        model.addAttribute("after", entityInformation.getAllAfter());
        model.addAttribute("loggedout", entityInformation.getAllLoggedOut());

        return "stats";
    }


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // if (status != null) {
        //     int statusCode = Integer.parseInt(status.toString());

            
        // }

        return "404";
    }
}
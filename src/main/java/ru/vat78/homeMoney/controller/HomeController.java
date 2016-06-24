package ru.vat78.homeMoney.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping({"/","/home"})
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ModelAndView showMainPage(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("home");
        return mv;
    }
}

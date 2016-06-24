package ru.vat78.homeMoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loginForm(){
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping(params = "error", method = RequestMethod.GET)
    public ModelAndView loginFormWithErros(Locale locale){
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("error", true);
        mv.addObject("message", "Error");
        return mv;
    }
}

package com.grandcircus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by MichaelRiley on 5/21/17.
 */


@Controller
public class HomeController {

    @RequestMapping ("/")//handler mapping
    public ModelAndView helloWorld(){//model and view method - has to return a model and view
        return new ModelAndView("welcome", "hello", "hello world");
    }

    @RequestMapping ("/register/family")//handler mapping
    public String registerFamily(){//model and view method - has to return a model and view
        return "newFamily";
    }

    @RequestMapping ("/register/user")//handler mapping
    public String registerUser(){//model and view method - has to return a model and view
        return "newUser";
    }

}




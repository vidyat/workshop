package com.sample.qwords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A Spring MVC controller class that handles the home page request.
 */
@Controller
public class HomeController {

    /**
     * Handles the request for the home page.
     *
     * @param model The Model object used to pass data to the view.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/")

    public String index(Model model) {

        return "home";

    }

}

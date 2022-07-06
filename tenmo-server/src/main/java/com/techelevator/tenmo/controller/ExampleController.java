package com.techelevator.tenmo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

public class ExampleController {

    @PreAuthorize("isAuthenticated()")
    @RestController
    public class exampleController{
        @RequestMapping(path="/test", method = RequestMethod.GET)
        public String test (Principal principal){
            return principal.getName();
        }

    }

}

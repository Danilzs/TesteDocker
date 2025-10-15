package com.example.TesteComDocker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {



    @GetMapping("/hello")
    public String Helo (){
        return "OLá mundo";
    }

    @GetMapping("/health")
    public String Heath (){
        return "OK OK";
    }




    }


package com.example.TesteComDocker.controller;

import java.time.LocalDateTime;
import java.util.List;

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


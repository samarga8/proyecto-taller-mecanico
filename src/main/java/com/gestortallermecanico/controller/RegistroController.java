package com.gestortallermecanico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroController {

    @GetMapping("/login")
    public String iniciarLogin(){
        return "login";
    }

}

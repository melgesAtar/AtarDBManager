package br.com.atardigital.AtarDBManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // O Spring Boot vai procurar por 'login.html' em 'src/main/resources/templates'
    }
}
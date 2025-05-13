package com.ssdd.taller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Página principal
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "Sistemas Distribuidos - Práctica 02");
        return "index";
    }

    // Pantalla de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Pantalla de acceso denegado
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

    @GetMapping("/gimnasios")
    public String gimnasios() { return "gimnasios"; }
}
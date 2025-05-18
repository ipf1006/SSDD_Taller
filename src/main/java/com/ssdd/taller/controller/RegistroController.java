package com.ssdd.taller.controller;

import com.ssdd.taller.dto.UsuarioPswDto;
import com.ssdd.taller.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mostrar el formulario vacío
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuarioPswDto", new UsuarioPswDto());
        return "registro";
    }

    // Procesar el POST
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuarioPswDto") UsuarioPswDto dto, BindingResult binding, Model model) {
        // Validaciones básicas de UsuarioPswDto (@Size, @NotBlank, @Email, @Pattern)
        if (binding.hasErrors()) {
            return "registro";
        }
        // ¿Coinciden ambas contraseñas?
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("errorConfirmacionPassword", "Las contraseñas no coinciden");
            return "registro";
        }

        try {
            usuarioService.registrar(dto);
            return "redirect:/registro?success";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorRegistro", ex.getMessage());
            return "registro";
        }
    }
}
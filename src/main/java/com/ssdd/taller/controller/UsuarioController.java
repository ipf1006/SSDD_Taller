package com.ssdd.taller.controller;

import com.ssdd.taller.dto.PerfilUsuarioDto;
import com.ssdd.taller.model.Usuario;
import com.ssdd.taller.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper mapper;

    public UsuarioController(UsuarioService usuarioService, ModelMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    /**
     * GET /usuario/perfil
     * Muestra el formulario con los datos actuales del USER.
     */
    @GetMapping("/perfil")
    public String verPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Recupera la entidad por username logueado
        Usuario entidad = usuarioService.buscarPorUsername(userDetails.getUsername());
        PerfilUsuarioDto dto = mapper.map(entidad, PerfilUsuarioDto.class);
        model.addAttribute("perfilUsuarioDto", dto);
        return "usuario-perfil";
    }

    /**
     * POST /usuario/perfil
     * Procesa cambios en username o email si fuera necesario
     */
    @PostMapping("/perfil")
    public String actualizarPerfil(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("perfilUsuarioDto") PerfilUsuarioDto dto, RedirectAttributes redirectAttributes, Model model) {
        try {
            // Llamamos al servicio y vemos si realmente se editó alguno de los campos
            boolean modificacionRealizada = usuarioService.actualizarDatosPorUser(userDetails.getUsername(), dto);
            if(modificacionRealizada) {
                // Forzar cierre de sesión (limpia el contexto de seguridad)
                SecurityContextHolder.clearContext();
                // Mensaje informativo
                redirectAttributes.addFlashAttribute("infoMessage", "Datos actualizados correctamente. Por favor, entra de nuevo.");
                // Redirigir a login
                return "redirect:/login";
            }else{
                model.addAttribute("warningMessage", "No se detectaron cambios en tu perfil.");
                return "usuario-perfil";
            }
        } catch (IllegalArgumentException ex) {
            // Si hay error (username o email duplicado)
            model.addAttribute("errorMessage", ex.getMessage());
            return "usuario-perfil";
        }
    }

    @PostMapping("/baja")
    public String darseDeBaja(@AuthenticationPrincipal UserDetails principal, RedirectAttributes redirectAttributes) {
        // Borrar la cuenta
        usuarioService.eliminarPorUsername(principal.getUsername());
        // Limpiar sesión
        SecurityContextHolder.clearContext();
        // Mensaje para login
        redirectAttributes.addFlashAttribute("infoMessage", "Tu cuenta ha sido dada de baja. ¡Hasta pronto!");
        return "redirect:/login";
    }
}
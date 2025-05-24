package com.ssdd.taller.controller;

import com.ssdd.taller.dto.UsuarioDto;
import com.ssdd.taller.dto.UsuarioPswDto;
import com.ssdd.taller.model.Usuario;
import com.ssdd.taller.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public AdminController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    /**
     * GET /admin/usuarios
     * Muestra la tabla con todos los usuarios cuyo rol sea "USER".
     */
    @GetMapping
    public String listarUsuarios(Model model) {
        List<UsuarioDto> usuarios = usuarioService.listarUsuariosDto();
        model.addAttribute("usuarios", usuarios);
        return "admin-usuarios";
    }

    /**
     * POST /admin/usuarios/{id}/eliminar
     * Elimina el usuario con el id dado y redirige a la misma lista.
     */
    @PostMapping("/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
        return "redirect:/admin/usuarios";
    }

    /**
     * GET /admin/usuarios/{id}/editar
     * Muestra un formulario de edición para ese usuario.
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);
        model.addAttribute("usuarioDto", usuarioDto);
        return "admin-editar-usuario";
    }

    /**
     * POST /admin/usuarios/{id}/editar
     * Procesa los cambios de username/email/rol para ese usuario.
     */
    @PostMapping("/{id}/editar")
    public String procesarEdicion(@PathVariable Long id, @ModelAttribute("usuarioDto") UsuarioDto usuarioDto, Model model) {
        // Lógica para actualizar username, email o rol (no password)
        try {
            usuarioService.actualizarDatosPorAdmin(id, usuarioDto);
            return "redirect:/admin/usuarios";
        } catch (IllegalArgumentException ex) {
            // Si hay duplicado de username o email
            model.addAttribute("mensajeError", ex.getMessage());
            model.addAttribute("usuarioDto", usuarioDto);
            return "admin-editar-usuario";
        }
    }

    /**
     * GET /admin/usuarios/crear
     * Muestra un formulario para crear un usuario.
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("usuarioPswDto", new UsuarioPswDto());
        return "admin-crear-usuario";
    }

    /**
     * POST /admin/usuarios/crear
     * Procesa el formulario para crear un usuario.
     */
    @PostMapping("/crear")
    public String procesarCrearUsuario(@Valid @ModelAttribute("usuarioPswDto") UsuarioPswDto dto, BindingResult binding, RedirectAttributes flash, Model model) {
        // Validaciones básicas de UsuarioPswDto (@Size, @NotBlank, @Email, @Pattern)
        if (binding.hasErrors()) {
            return "admin-crear-usuario";
        }
        // ¿Coinciden ambas contraseñas?
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("errorConfirmacionPassword", "Las contraseñas no coinciden");
            return "admin-crear-usuario";
        }
        try {
            usuarioService.crearUsuarioDesdeAdmin(dto);
            flash.addFlashAttribute("mensajeOK", "Usuario '" + dto.getUsername() + "' creado correctamente.");
            return "redirect:/admin/usuarios/crear";
        } catch (IllegalArgumentException ex) {
            flash.addFlashAttribute("mensajeError", ex.getMessage());
            return "redirect:/admin/usuarios/crear";
        }
    }
}
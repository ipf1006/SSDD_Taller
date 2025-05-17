package com.ssdd.taller.controller;

import com.ssdd.taller.dto.UsuarioDto;
import com.ssdd.taller.model.Usuario;
import com.ssdd.taller.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * POST /admin/users/{id}/editar
     * Procesa los cambios de username/email/rol para ese usuario.
     */
    @PostMapping("/{id}/editar")
    public String procesarEdicion(@PathVariable Long id, @ModelAttribute("usuarioDto") UsuarioDto usuarioDto) {
        // Lógica para actualizar username, email o rol (no password)
        usuarioService.actualizarDatos(id, usuarioDto);
        return "redirect:/admin/usuarios";
    }
}
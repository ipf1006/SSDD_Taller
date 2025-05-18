package com.ssdd.taller.service;

import com.ssdd.taller.dto.PerfilUsuarioDto;
import com.ssdd.taller.dto.UsuarioDto;
import com.ssdd.taller.dto.UsuarioPswDto;
import com.ssdd.taller.model.Usuario;
import com.ssdd.taller.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Recupera todos los usuarios con rol USER y los convierte a UsuarioDto.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDto> listarUsuariosDto() {
        // Lee todas las entidades
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Prepara el iterador y la lista de DTOs vacía
        Iterator<Usuario> it = usuarios.iterator();
        List<UsuarioDto> dtos = new ArrayList<>();

        // Recorre todas las entidades
        while (it.hasNext()) {
            Usuario u = it.next();
            // Solo mantenemos las de rol "USER"
            if (!"USER".equals(u.getRole())) {
                continue;
            }
            // Para cada entidad, crea un DTO vacío y mapea
            UsuarioDto dto = new UsuarioDto();
            modelMapper.map(u, dto);

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * Lista todos los usuarios de la base de datos.
     */
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID o lanza IllegalArgumentException si no existe.
     */
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario con id " + id + " no encontrado"));
    }

    /**
     * Busca un usuario por su username o lanza IllegalArgumentException si no existe.
     */
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Usuario " + username + " no encontrado"));
    }

    /**
     * Registra un nuevo usuario a partir del DTO con contraseña.
     * Comprueba duplicados y encripta la password.
     */
    @Transactional
    public Usuario registrar(UsuarioPswDto usuarioPswDto) {
        // Comprueba duplicados
        if (usuarioRepository.existsByUsername(usuarioPswDto.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe.");
        }
        if (usuarioRepository.existsByEmail(usuarioPswDto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        // Construcción de la entidad
        Usuario u = new Usuario();
        u.setUsername(usuarioPswDto.getUsername());
        u.setEmail(usuarioPswDto.getEmail());
        u.setPassword(passwordEncoder.encode(usuarioPswDto.getPassword()));
        u.setRole("USER");
        // Persiste
        return usuarioRepository.save(u);
    }

    /**
     * Elimina un usuario dado su ID.
     */
    @Transactional
    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de un usuario existente.
     */
    @Transactional
    public void actualizarDatosPorAdmin(Long id, UsuarioDto dto) {
        Usuario u = buscarPorId(id);

        // Evitar duplicar username o email en otros registros
        Optional<Usuario> datoExistente = usuarioRepository.findByUsername(dto.getUsername());
        if (datoExistente.isPresent() && !datoExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
        datoExistente = usuarioRepository.findByEmail(dto.getEmail());
        if (datoExistente.isPresent() && !datoExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        // Asignar nuevos valores
        modelMapper.map(dto, u);

        usuarioRepository.save(u);
    }

    /**
     * El propio usuario actualiza sus datos.
     */
    @Transactional
    public boolean actualizarDatosPorUser(String username, PerfilUsuarioDto dto) {
        Usuario entidad = buscarPorUsername(username);
        boolean modificacionRealizada = false;

        // Compruebo duplicados igual que antes
        Optional<Usuario> datoExistente = usuarioRepository.findByUsername(dto.getUsername());
        if (datoExistente.isPresent() && !datoExistente.get().getId().equals(entidad.getId())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
        datoExistente = usuarioRepository.findByEmail(dto.getEmail());
        if (datoExistente.isPresent() && !datoExistente.get().getId().equals(entidad.getId())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        if (!entidad.getUsername().equals(dto.getUsername()) || !entidad.getEmail().equals(dto.getEmail())) {
            modelMapper.map(dto, entidad);
            modificacionRealizada = true;
            usuarioRepository.save(entidad);
        }

        return modificacionRealizada;
    }

    /**
     * El propio usuario elimina su cuenta.
     */
    @Transactional
    public void eliminarPorUsername(String username) {
        Usuario u = usuarioRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Usuario “" + username + "” no encontrado."));
        usuarioRepository.delete(u);
    }
}
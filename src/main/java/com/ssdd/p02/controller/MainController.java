package com.ssdd.p02.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdd.p02.model.Usuario;
import com.ssdd.p02.service.FlaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final FlaskService flaskService;

    @Autowired
    public MainController(FlaskService flaskService) {
        this.flaskService = flaskService;
    }

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

    // Pantalla de gestión de excepciones
    @GetMapping("/api")
    public String api() {
        return "gestion-excepciones";
    }

    // Pantalla de acceso denegado
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

    // Maneja las peticiones GET a la URL "/usuarios"
    @GetMapping("/api/db/listado-usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = flaskService.getUsuarios();
        // Añade la lista de usuarios al modelo para que esté disponible en la vista Thymeleaf
        // La clave "usuarios" será usada en el HTML para recorrer e imprimir los datos
        model.addAttribute("usuarios", usuarios);
        // Devuelve el nombre de la vista que se renderizará (usuarios.html en /templates/)
        return "usuarios";
    }

    @GetMapping("/api/db/tabla-inexistente")
    public String errorTablaInexistente(Model model) {
        String resultado = flaskService.consultarTablaInexistente();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/db/conexion-fallida")
    public String errorConexionFallida(Model model) {
        String resultado = flaskService.conexionFallida();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/db/valores-duplicados")
    public String errorValoresDuplicados(Model model) {
        String resultado = flaskService.insertarValoresDuplicados();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/db/valores-nulos")
    public String errorValoresNulos(Model model) {
        String resultado = flaskService.insertarValoresNulos();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/externa/recurso-existente")
    public String apiExternaRecursoExistente(Model model) {
        String resultado = flaskService.apiExternaRecursoExistente();
        // Procesar el JSON recibido para extraer datos específicos
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(resultado);

            model.addAttribute("nombre", rootNode.get("nombre").asText());
            model.addAttribute("capital", rootNode.get("capital").asText());
            model.addAttribute("bandera", rootNode.get("bandera").asText());
        } catch (Exception e) {
            model.addAttribute("resultado", "Error al procesar el JSON recibido");
            model.addAttribute("tipoModal", "error");
        }
        return "api-externa-recurso-existente";
    }

    @GetMapping("/api/externa/recurso-inexistente")
    public String errorApiExternaRecursoInexistente(Model model) {
        String resultado = flaskService.apiExternaRecursoInexistente();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/externa/solicitud-erronea")
    public String errorApiExternaSolicitudErronea(Model model) {
        String resultado = flaskService.apiExternaSolicitudErronea();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/externa/archivo/correcto")
    public String apiExternaArchivoExistente(Model model) {
        String resultado = flaskService.apiExternaArchivoExistente();
        return getContenidoArchivo(model, resultado);
    }

    @GetMapping("/api/externa/archivo/inexistente")
    public String errorApiExternaArchivoInexistente(Model model) {
        String resultado = flaskService.apiExternaArchivoInexistente();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipoModal", "error");
        return "gestion-excepciones";
    }

    @GetMapping("/api/externa/archivo/restringido")
    public String errorApiExternaArchivoRestringido(Model model) {
        String resultado = flaskService.apiExternaArchivoRestringido();
        return getContenidoArchivo(model, resultado);
    }

    private String getContenidoArchivo(Model model, String endpointFlask) {
        try {
            String json = flaskService.getResponse(endpointFlask);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode nodo = mapper.readTree(json);

            String nombreArchivo = nodo.get("mensaje").asText();
            String contenido = nodo.get("contenido").asText();

            model.addAttribute("archivo", nombreArchivo);
            model.addAttribute("contenido", contenido);
            model.addAttribute("tipoModal", "info");

        } catch (Exception e) {
            model.addAttribute("resultado", "Error al procesar la respuesta: " + e.getMessage());
            model.addAttribute("tipoModal", "error");
        }
        return "gestion-excepciones";
    }
}
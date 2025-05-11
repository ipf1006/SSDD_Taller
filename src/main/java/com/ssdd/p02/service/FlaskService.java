package com.ssdd.p02.service;

import com.ssdd.p02.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service // De este modo Spring la gestiona como un bean
public class FlaskService {

    // Inyecta el valor de la propiedad definida en application.properties
    @Value("${api.flask.url}")
    private String flaskUrl;

    // Objeto de Spring para realizar llamadas HTTP (GET, POST...)
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Método para hacer una petición GET al microservicio Flask
     * Captura y maneja las posibles excepciones
     *
     * @param endpoint Ruta relativa
     * @return Respuesta de Flask o mensaje de error procesado
     */
    public String getResponse(String endpoint) {
        try {
            String url = flaskUrl + endpoint;
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            // Manejo de errores centralizado
            return handleError(e);
        }
    }

    /**
     * Método que invoca al microservicio Flask y transforma el JSON recibido en una lista de objetos Usuario
     *
     * @return Lista de usuarios o lista vacía en caso de error
     */
    public List<Usuario> getUsuarios() {
        try {
            String url = flaskUrl + "/api/db/listado-usuarios";
            Usuario[] usuarios = restTemplate.getForObject(url, Usuario[].class);
            return Arrays.asList(Objects.requireNonNull(usuarios, "No se encontraron usuarios")); // Convertimos el array de usuarios en una lista Java
        } catch (RestClientException e) {
            handleError(e); // Capturamos el error para gestionarlo
            return Collections.emptyList(); // Devolvemos una lista vacía en caso de error
        }
    }

    // API PROPIA - Método para simular error de "Consultar tabla inexistente"
    public String consultarTablaInexistente() {
        return getResponse("/api/db/tabla-inexistente");
    }

    // API PROPIA - Método para simular error de "Conexión fallida"
    public String conexionFallida() {
        return getResponse("/api/db/conexion-fallida");
    }

    // API PROPIA - Método para simular error de "Insertar valores duplicados"
    public String insertarValoresDuplicados() {
        return getResponse("/api/db/valores-duplicados");
    }

    // API PROPIA - Método para simular error de "Insertar valores nulos"
    public String insertarValoresNulos() {
        return getResponse("/api/db/valores-nulos");
    }

    // API EXTERNA - Método para mostrar un "Recurso existente"
    public String apiExternaRecursoExistente() {
        return getResponse("/api/externa/recurso-existente");
    }

    // API EXTERNA - Método para simular error de "Recurso inexistente"
    public String apiExternaRecursoInexistente() {
        return getResponse("/api/externa/recurso-inexistente");
    }

    // API EXTERNA - Método para simular error de "Solicitud errónea"
    public String apiExternaSolicitudErronea() {
        return getResponse("/api/externa/solicitud-erronea");
    }

    // API PROPIA - Método para leer un archivo correcto
    public String apiExternaArchivoExistente() {
        return "/api/externa/archivo/correcto";
    }

    // API PROPIA - Método para intentar leer un archivo que no existe
    public String apiExternaArchivoInexistente() {
        return getResponse("/api/externa/archivo/inexistente");
    }

    // API PROPIA - Método para intentar leer un archivo con acceso restringido
    public String apiExternaArchivoRestringido() {
        return "/api/externa/archivo/restringido";
    }

    // Centralización del manejo de errores
    private String handleError(RestClientException e) {

        String errorMessage = e.getMessage();

        if (e.getMessage().contains("404") || e.getMessage().contains("Not Found")) {
            errorMessage = "El recurso solicitado no fue encontrado.";
        } else if (e.getMessage().contains("504")) {
            errorMessage = "Tiempo de espera agotado. La solicitud ha excedido el tiempo máximo de espera.";
        } else if (e.getMessage().contains("409")) {
            errorMessage = "No se pudo realizar la acción, ya existe un registro con estos datos.";
        } else if (e.getMessage().contains("400") || e.getMessage().contains("Bad Request")) {
            errorMessage = "La solicitud no se puede procesar porque algunos de los campos requeridos están vacíos";
        } else if (e.getMessage().contains("500")){
            errorMessage = "ERROR 500 - Ha ocurrido un error inesperado.";
        }
        return errorMessage;
    }


}

package com.ssdd.taller.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException {

        // Obtenemos la primera (y única) autoridad del usuario, es decir, el role
        String role = auth.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_ADMIN".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/admin/usuarios");
        } else {
            resp.sendRedirect(req.getContextPath() + "/usuario/perfil");
        }
    }
}
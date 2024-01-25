package com.team.leaf.user.account.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.leaf.user.account.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    private final HttpSession httpSession;
    private final ObjectMapper objectMapper;

    public AuthenticationSuccessHandler(HttpSession httpSession, ObjectMapper objectMapper) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        if(authentication.isAuthenticated()) {
            response.setStatus(HttpStatus.OK.value());
            User user = (User) httpSession.getAttribute("user");

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Authentication successful");
            responseBody.put("userName", user.getName());
            responseBody.put("userEmail", user.getEmail());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
            response.getWriter().flush();
        }
        else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Authentication failed");
        }
    }
}

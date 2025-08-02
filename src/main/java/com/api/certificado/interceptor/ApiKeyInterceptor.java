package com.api.certificado.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.api.certificado.annotation.RequireApiKey;
import com.api.certificado.config.security.ApiSecurityProperties;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final ApiSecurityProperties apiSecurityProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        if (!apiSecurityProperties.isEnabled()) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        RequireApiKey requireApiKey = method.getAnnotation(RequireApiKey.class);
        if (requireApiKey == null) {
            return true;
        }

        String headerName = requireApiKey.value().isEmpty() ? 
            apiSecurityProperties.getHeaderName() : requireApiKey.value();

        String apiKey = request.getHeader(headerName);
        
        if (apiKey == null || apiKey.trim().isEmpty()) {
            log.warn("API Key ausente para endpoint: {} {}", request.getMethod(), request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "error": "API_KEY_AUSENTE",
                    "message": "API Key é obrigatória para este endpoint",
                    "status": 401
                }
                """);
            return false;
        }

        List<String> validKeys = apiSecurityProperties.getValidKeys();
        if (validKeys == null || !validKeys.contains(apiKey)) {
            log.warn("API Key inválida para endpoint: {} {} - Key: {}", 
                    request.getMethod(), request.getRequestURI(), apiKey);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "error": "API_KEY_INVALIDA",
                    "message": "API Key inválida",
                    "status": 401
                }
                """);
            return false;
        }

        log.debug("API Key válida para endpoint: {} {}", request.getMethod(), request.getRequestURI());
        return true;
    }
}

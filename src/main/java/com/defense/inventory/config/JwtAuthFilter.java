package com.defense.inventory.config;

import com.defense.inventory.entity.User;
import com.defense.inventory.service.impl.UserServiceImpl;
import com.defense.inventory.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestToken = request.getHeader("Authorization");

        if (requestToken == null || !requestToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestToken.split("Bearer ")[1];

        try {
            Long userId = jwtUtils.getUserIdFromToken(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getUserByIdJWT(userId);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Token has expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Token has expired. Please login again.\", \"status\": false}");
            return; // stop filter chain execution
        } catch (io.jsonwebtoken.JwtException e) {
            // Invalid token
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Invalid token. Authentication failed.\", \"status\": false}");
            return;
        }

        filterChain.doFilter(request, response);
    }

}

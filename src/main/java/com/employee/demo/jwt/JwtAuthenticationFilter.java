package com.employee.demo.jwt;


import com.employee.demo.exception.CustomSecurityException;
import com.employee.demo.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtToken jwtToken;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver exceptionResolver;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtToken jwtToken, UserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtToken = jwtToken;
        this.userDetailsService = userDetailsService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);

            if (StringUtils.hasText(token) && jwtToken.validateToken(token)) {
                String username = jwtToken.getUsername(token);
                log.debug("Username extracted from token: {}", username);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                log.debug("UserDetails loaded: {}", userDetails);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            log.info("working {}", response);
            filterChain.doFilter(request, response);
        } catch (CustomSecurityException ex) {
            log.error("CustomSecurityException: ", ex);
            exceptionResolver.resolveException(request, response, null, ex);
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context {}", ex);
            exceptionResolver.resolveException(request, response, null, new CustomSecurityException("Invalid token or user details"));
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

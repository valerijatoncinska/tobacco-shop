package de.shop.modules.users.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс фильтр для токенов
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(CustomDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Старт фильтра");
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Filter. Получен заголовок. " + authorizationHeader);
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("Filter. Чистый заголовок. " + jwt);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("filter. получено имя. " + username);
            } catch (Exception e) {
                System.err.println("JWT extraction error: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("Filter. Найдено в базе. " + userDetails.getUsername());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                System.out.println("filter. токен валиден");
                List<String> roles = jwtUtil.extractRoles(jwt);
                Collection<? extends GrantedAuthority> authorities = roles.stream()
                        .map(role -> (GrantedAuthority) () -> role)
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
        System.out.println("Filter. конец");
    }
}

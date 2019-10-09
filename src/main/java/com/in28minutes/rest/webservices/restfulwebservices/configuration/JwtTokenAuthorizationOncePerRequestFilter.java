package com.in28minutes.rest.webservices.restfulwebservices.configuration;

import com.in28minutes.rest.webservices.restfulwebservices.service.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private UserDetailsService todoUserDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.fine("Authentication Request For '{}' " + request.getRequestURL());
        if (!request.getRequestURL().toString().contains("/users/control")) {
            var requestTokenHeader = request.getHeader(this.tokenHeader);

            String username = null;
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtTokenService.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    logger.severe("JWT_TOKEN_UNABLE_TO_GET_USERNAME \n" + e);
                } catch (ExpiredJwtException e) {
                    logger.info("JWT_TOKEN_EXPIRED \n" + e);
                }
            } else {
                logger.info("JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING");
            }

            logger.fine("JWT_TOKEN_USERNAME_VALUE '{}' " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetails = this.todoUserDetailsService.loadUserByUsername(username);

                if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                    var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

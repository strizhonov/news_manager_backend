package com.epam.lab.jwt;

import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String authHeader;

    @Value("${jwt.startsWith}")
    private String authHeaderBeginning;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain chain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(authHeader);

        if (headerFits(authorizationHeader)) {
            processHeaderForAuthentication(authorizationHeader);
        }

        chain.doFilter(request, response);

    }


    private boolean headerFits(final String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.split(" ").length == 2
                && authorizationHeader.split(" ")[0].equalsIgnoreCase(authHeaderBeginning);
    }


    private void processHeaderForAuthentication(final String authorizationHeader) {
        String jwt = authorizationHeader.split(" ")[1];
        String username = jwtUtil.extractUsername(jwt);

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (tokenIsValid(jwt, userDetails)) {
            setAuthentication(userDetails);
        }

    }


    private boolean tokenIsValid(final String jwt, final UserDetails userDetails) {
        return jwtUtil.validateToken(jwt, userDetails);
    }


    private void setAuthentication(final UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }


}
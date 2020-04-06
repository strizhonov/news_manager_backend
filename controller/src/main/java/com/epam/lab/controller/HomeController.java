package com.epam.lab.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Home controller is intended to redirect from the empty home page
 * to the all news listing
 */
@RestController
@RequestMapping("/")
public class HomeController {

    private static final String REDIRECT_TO = "/news";

    @PreAuthorize("permitAll()")
    @GetMapping
    public void home(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + REDIRECT_TO);
    }

}

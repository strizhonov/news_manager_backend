package com.epam.lab.controller;

import com.epam.lab.dto.SignInDto;
import com.epam.lab.dto.UserDto;
import com.epam.lab.jwt.JwtUtil;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticates user and return JWT for further authorization.
     *
     * @param signInDto to authenticate
     * @return response with JWT inside its body
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody final SignInDto signInDto) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(signInDto.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateSignedToken(userDetails));
    }

    /**
     * Save User into the database.
     * <p>
     * If Author is successfully added, response code "201" to return. Otherwise user gets a
     * code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param userToCreate to insert into the database
     * @return response with created user
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody final UserDto userToCreate) {
        UserDto createdUser = userService.create(userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


}

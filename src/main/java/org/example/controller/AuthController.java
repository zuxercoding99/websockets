package org.example.controller;

import org.example.config.JwtUtils;
import org.example.payload.LoginResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils) {
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody java.util.Map<String, String> creds, HttpServletResponse response) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.get("username"), creds.get("password")));
        String token = jwtUtils.generateToken("user"); // creds.get("username")
        Cookie cookie = new Cookie("jwt", token);
        System.out.println("User creds: " + creds.get("username"));

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false); // solo en HTTPS
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return new LoginResponse(token);
    }
}
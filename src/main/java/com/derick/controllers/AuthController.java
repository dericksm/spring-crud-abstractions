package com.derick.controllers;//package com.derick.controllers;

import com.derick.entities.dto.EmailDTO;
import com.derick.security.JWTUtils;
import com.derick.security.UserDetailsImpl;
import com.derick.services.AuthService;
import com.derick.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserDetailsImpl user = UserService.authenticatedUser();
        String token = jwtUtils.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }

}

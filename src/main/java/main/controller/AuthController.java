package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.RegisteredUserDTO;
import main.dto.TokenDTO;
import main.dto.LoginDTO;
import main.model.RegisteredUser;
import main.model.Role;
import main.service.AuthService;
import main.util.TokenUtils;

import java.util.List;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDTO> register(@RequestBody RegisteredUserDTO userDTO) {
        RegisteredUser user = service.register(userDTO);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<String> roles = null;
        if (!user.getRoles().isEmpty()) {
            roles = user.getRoles().stream().map(Role::getName).toList();
        }

        return new ResponseEntity<>(
                new RegisteredUserDTO(
                        user.getUsername(),
                        null,
                        user.getEmail(),
                        roles
                ),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO korisnikDTO) {
        RegisteredUser user = service.findByUsernameAndPassword(korisnikDTO.getEmail(), korisnikDTO.getPassword());

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        String jwt = tokenUtils.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
       TokenDTO token = new TokenDTO(jwt);
        for (Role role : user.getRoles()) {
            token.getRoles().add(role.getName());
        }

        System.out.println(jwt);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
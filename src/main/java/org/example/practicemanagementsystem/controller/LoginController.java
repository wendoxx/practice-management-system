package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.dto.request.LoginRequestDTO;
import org.example.practicemanagementsystem.dto.request.RegisterRequestDTO;
import org.example.practicemanagementsystem.dto.response.LoginResponseDTO;
import org.example.practicemanagementsystem.infra.TokenService;
import org.example.practicemanagementsystem.model.UserModel;
import org.example.practicemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = this.tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        if(this.userRepository.findByUsername(registerRequestDTO.getUsername()) != null) {
            return ResponseEntity.badRequest().build();
        } else {

            String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword());
            UserModel user = new UserModel(registerRequestDTO.getUsername(), encryptedPassword, registerRequestDTO.getRole());

            this.userRepository.save(user);
        }
        return ResponseEntity.ok().build();
    }


}

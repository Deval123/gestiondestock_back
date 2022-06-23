package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.dto.auth.AuthenticationRequest;
import com.deval.gestiondestock.dto.auth.AuthenticationResponse;
import com.deval.gestiondestock.model.auth.ExtendedUser;
import com.deval.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.deval.gestiondestock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.deval.gestiondestock.utils.Constants.AUTHENTIFICATION_ENDPOINT;

@RestController
@RequestMapping(AUTHENTIFICATION_ENDPOINT) //car il ne sera pas declaré dans l'interface
public class AuthenticationController {

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;  //injection du Bean dans SecurityConfig

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        System.out.println(request.getLogin() + "................" + request.getPassword());
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()

                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails); //Pour générer le token et le renvoyer en tant que reponse

        System.out.println(jwt);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }
}

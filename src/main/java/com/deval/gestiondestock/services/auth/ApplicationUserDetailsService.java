package com.deval.gestiondestock.services.auth;

import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.model.auth.ExtendedUser;
import com.deval.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UtilisateurDto utilisateurDto = service.findByEmail(email);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        utilisateurDto.getRoles().forEach(rolesDto -> authorities.add(new SimpleGrantedAuthority(rolesDto.getRoleName())));

        return new ExtendedUser(utilisateurDto.getEmail(), utilisateurDto.getMotDePasse(), utilisateurDto.getEntreprise().getId(), authorities);

    }
}

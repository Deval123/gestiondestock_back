package com.deval.gestiondestock.config;

import com.deval.gestiondestock.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationRequestFilter applicationRequestFilter;

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(applicationUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests().antMatchers("/**/authenticate",
                        "/**/entreprise/create",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/v1",
                        "/configuration/security",
                        "/swagger-ui/index.html",
                        "webjars/**",
                        "v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui/index.html/utilisateur-controller").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //par defaut c'est Restfull (on ne garde pas le statut du client au n iveau de l'api)

        //pour la securité utilisé ce filtre avant le démarrage de l'application
        http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
//
    @Bean
    public AuthenticationManager customAuthenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

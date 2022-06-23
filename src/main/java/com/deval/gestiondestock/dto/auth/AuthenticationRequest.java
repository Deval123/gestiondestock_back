package com.deval.gestiondestock.dto.auth;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String login;
    private String password;

}

package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Adresses;
import com.deval.gestiondestock.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClientDto {

    private Integer id;

    private  String nom;

    private  String prenom;

    private Adresses adresse;

    private  String photo;

    private  String mail;

    private  String numTel;

    private  Integer identreprise;

    @JsonIgnore
    private List<CommandeClientDto> commandeclient;

    public static ClientDto fromEntity(Client client){
        if (client == null){
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(client.getAdresse())
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .identreprise(client.getIdentreprise())
/*                .commandeclient(
                        client.getCommandeclient() != null ?
                                client.getCommandeclient().stream()
                                        .map(CommandeClientDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )*/
                .build();
    }

    public static Client toEntity(ClientDto clientDto){
        if(clientDto == null){
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setAdresse(clientDto.getAdresse());
        client.setPhoto(clientDto.getPhoto());
        client.setNumTel(clientDto.getNumTel());
        client.setMail(clientDto.getMail());
        client.setIdentreprise(clientDto.getIdentreprise());
        //client.setCommandeclient(CommandeClientDto.toEntity(clientDto.getCommandeclient()));
        return client;
    }
}

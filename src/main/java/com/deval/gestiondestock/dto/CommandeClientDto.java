package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.CommandeClient;
import com.deval.gestiondestock.model.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;

    private  String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private ClientDto client;

    private  Integer identreprise;

    @JsonIgnore
    private List<LigneCommandeClientDto> lignecommandeclients;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient){
        if (commandeClient == null){
            return null;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .identreprise(commandeClient.getIdentreprise())
                .etatCommande(commandeClient.getEtatCommande())
/*                .lignecommandeclient(
                        commandeClient.getLignecommandeclient() != null?
                                commandeClient.getLignecommandeclient().stream()
                                        .map(LigneCommandeClientDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )*/
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){
        if(commandeClientDto == null){
            return null;

        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));
        commandeClient.setIdentreprise(commandeClientDto.getIdentreprise());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
        //commandeClient.setLignecommandeclient(LigneCommandeClientDto.toEntity(commandeClientDto.getLignecommandeclient()));
        return commandeClient;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}

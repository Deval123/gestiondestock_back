package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.*;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.*;
import com.deval.gestiondestock.repository.ArticleRepository;
import com.deval.gestiondestock.repository.ClientRepository;
import com.deval.gestiondestock.repository.CommandeClientRepository;
import com.deval.gestiondestock.repository.LigneCommandeClientRepository;
import com.deval.gestiondestock.services.CommandeClientService;
import com.deval.gestiondestock.services.MvtStockService;
import com.deval.gestiondestock.validator.ArticleValidator;
import com.deval.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommandeClientServiceImpl implements CommandeClientService{

    private CommandeClientRepository commandeClientRepository;

    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;

    private MvtStockService mvtStockService;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, LigneCommandeClientRepository ligneCommandeClientRepository,
                     ClientRepository clientRepository, ArticleRepository articleRepository, MvtStockService mvtStockService) {
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.mvtStockService = mvtStockService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        List<String> errors = CommandeClientValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Commande Client is not valide", dto);
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }


        if(dto.getId() != null && dto.isCommandeLivree()){
            log.error("Commande Client a été déjà livrée", dto);
            throw new InvalidOperationException("Impossible de modifier la commande client lorsqu'elle est livrée",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if(client.isEmpty()){
            log.warn("Client with ID {} was not found in the database", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID" + dto.getClient().getId() +"n'a été trouvé dans la BD", ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();
        if(dto.getLignecommandeclients() != null){
            dto.getLignecommandeclients().forEach(ligCmdClt ->{
                if(ligCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                    if(article.isEmpty()){
                        articleErrors.add("L'article avec l'ID" + ligCmdClt.getArticle().getId() + "n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer avec un article null");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("L'article n'existe pas dans la base de donnée");
            throw new InvalidEntityException("L'article n'existe pas dans la base de donnée", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        if(dto.getLignecommandeclients() != null){
            dto.getLignecommandeclients().forEach(ligCmdClt ->{
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClient(savedCmdClt);
                ligneCommandeClientRepository.save(ligneCommandeClient);

            });
        }

        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null){
            log.error("Commande client ID is NULL");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a été trouvé avec l'ID" + id,  ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
        ));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande client CODE is NULL");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a été trouvé avec le CODE :" + code,  ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Commande client id is NULL");
            return ;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllLigneCommandesClientByCommandeClientId(id);
        if (!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande client déjà utilisée", ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
        }
        commandeClientRepository.deleteById(id);
    }

    @Override
    public List<LigneCommandeClientDto> findAllLigneCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllLigneCommandesClientByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);

        if(StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'état de la commande  client est NULL");
            throw new InvalidOperationException("Impossible de modifier la commande client lorsqu'elle est livrée",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
        commandeClientDto.setEtatCommande(etatCommande);
        CommandeClient savedCommandeClient = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));
        if(commandeClientDto.isCommandeLivree()) {
            updateMvtStock(idCommande);
        }
        return CommandeClientDto.fromEntity(savedCommandeClient);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {

        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if(quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("La quantite de la commande client est NULL");
            throw new InvalidOperationException("Impossible de modifier la commande avec une ligne de commande nulle",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);

        if(idClient == null){
            log.error("L'id  du client est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ID client null",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);


        Optional<Client> clientOptional =clientRepository.findById(idClient);

        if (clientOptional.isEmpty()){
            log.error("L'id n'appartient à aucun client");
            throw new InvalidOperationException("Aucun client n'a été trouvée dans la base de donnée avec l'id =" + idClient,  ErrorCodes.CLIENT_NOT_FOUND);
        }
        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient)));
    }


    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
            if(articleOptional.isEmpty()){
                //log.error("article est NULL");
                throw new EntityNotFoundException("Aucun article avec l'ID =" + idArticle +" n'a été trouvée dans la base de donnée",  ErrorCodes.ARTICLE_NOT_FOUND);
            }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));

        if(!errors.isEmpty()){
            //log.error("article est NULL");
            throw new InvalidEntityException("article invalid",  ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeClient and inform the client in case it's absent
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier la commande client avec l'id null",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande){
        if(idLigneCommande == null){
            log.error("L'id  de la ligne commande client est NULL");
            throw new InvalidOperationException("Impossible de modifier la commande avec une ligne de commande nulle",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

    }

    private void checkIdArticle(Integer idArticle, String msg){
        if(idArticle == null){
            log.error("L'id  de "+ msg +"est NULL");
            throw new InvalidOperationException("Impossible de modifier la commande avec un" + msg + "ID article null",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

    }

    private CommandeClientDto checkEtatCommande(Integer idCommande) {
        CommandeClientDto commandeClientDto = findById(idCommande);
        if(commandeClientDto.isCommandeLivree()){
            //log.error("L'id de la commande  client est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ID client null",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClientDto;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommandeClient){
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommandeClient);
        if(ligneCommandeClientOptional.isEmpty()){
           // log.error("La ligne commande client est NULL");
            throw new EntityNotFoundException("Aucune ligne commande client avec l'ID =" + idLigneCommandeClient +" n'a été trouvée dans la base de donnée",  ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private void updateMvtStock(Integer idCommande){
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllLigneCommandesClientByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig -> {
            MvtStockDto mvtStockDto = MvtStockDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStock.SORTIE)
                    .sourceMvtStock(SourceMvtStock.COMMANDE_CLIENT)
                    .quantite(lig.getQuantite())
                    .identreprise(lig.getIdentreprise())
                    .build();
            mvtStockService.sortieStock(mvtStockDto);
        });
    }
}

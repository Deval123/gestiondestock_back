package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.*;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.*;
import com.deval.gestiondestock.repository.*;
import com.deval.gestiondestock.services.CommandeFournisseurService;
import com.deval.gestiondestock.services.MvtStockService;
import com.deval.gestiondestock.validator.ArticleValidator;
import com.deval.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
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
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;

    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    private FournisseurRepository fournisseurRepository;

    private ArticleRepository articleRepository;

    private MvtStockService mvtStockService;

    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, FournisseurRepository fournisseurRepository,
          ArticleRepository articleRepository, MvtStockService mvtStockService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvtStockService = mvtStockService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Commande Fournisseur is not valid", dto);
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if(fournisseur.isEmpty()){
            log.warn("Fournisseur with ID {} was not found in the database", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun Fournisseur avec l'ID" + dto.getFournisseur().getId() +"n'a été trouvé dans la BD", ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();
        if(dto.getLignecommandefournisseur() != null){
            dto.getLignecommandefournisseur().forEach(ligCmdClt ->{
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

        CommandeFournisseur savedCmdfournisseur = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

        if(dto.getLignecommandefournisseur() != null){
            dto.getLignecommandefournisseur().forEach(ligCmdFournisseur ->{
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFournisseur);
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmdfournisseur);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

            });
        }

        return CommandeFournisseurDto.fromEntity(savedCmdfournisseur);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);

        if(StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'état de la commande  Fournisseur est NULL");
            throw new InvalidOperationException("Impossible de modifier la commande Fournisseur lorsqu'elle est livrée",  ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);
        commandeFournisseurDto.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommandeFournisseur = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));
        if(commandeFournisseurDto.isCommandeLivree()) {
            updateMvtStock(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommandeFournisseur);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if(quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("La quantite de la commande Fournisseur est NULL");
            throw new InvalidOperationException("Impossible de modifier la commande avec une ligne de commande nulle",  ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseuOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseuOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);

        if(idClient == null){
            log.error("L'id  du client est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ID client null",  ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);


        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idClient);

        if (fournisseurOptional.isEmpty()){
            log.error("L'id n'appartient à aucun fournisseur");
            throw new InvalidOperationException("Aucun fournisseur n'a été trouvée dans la base de donnée avec l'id =" + idClient,  ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur)));
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

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
        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeFournisseur and inform the client in case it's absent
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Commande Fournisseur ID is NULL");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande Fournisseur n'a été trouvé avec l'ID" + id,  ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }
    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande Fournisseur CODE is NULL");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande Fournisseur n'a été trouvé avec le CODE :" + code,  ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLigneCommandesClientByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllLigneCommandesFournisseurByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Commande Fournisseur id is NULL");
            return ;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllLigneCommandesFournisseurByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur déjà utilisée", ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }


    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier la commande FOURNISSEUR avec l'id null",  ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
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

    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        if(commandeFournisseurDto.isCommandeLivree()){
            //log.error("L'id de la commande  client est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ID fournisseur null",  ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseurDto;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommandeFournisseur){
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommandeFournisseur);
        if(ligneCommandeFournisseurOptional.isEmpty()){
            // log.error("La ligne commande client est NULL");
            throw new EntityNotFoundException("Aucune ligne commande Fournisseur avec l'ID =" + idLigneCommandeFournisseur +" n'a été trouvée dans la base de donnée",  ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void updateMvtStock(Integer idCommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllLigneCommandesFournisseurByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseurs.forEach(lig -> {
            MvtStockDto mvtStockDto = MvtStockDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStock.ENTREE)
                    .sourceMvtStock(SourceMvtStock.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .identreprise(lig.getIdentreprise())
                    .build();
            mvtStockService.sortieStock(mvtStockDto);
        });
    }



}

package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.FournisseurDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.CommandeClient;
import com.deval.gestiondestock.model.CommandeFournisseur;
import com.deval.gestiondestock.model.Fournisseur;
import com.deval.gestiondestock.repository.CommandeFournisseurRepository;
import com.deval.gestiondestock.repository.FournisseurRepository;
import com.deval.gestiondestock.services.FournisseurService;
import com.deval.gestiondestock.validator.ArticleValidator;
import com.deval.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, CommandeFournisseurRepository commandeFournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if(!errors.isEmpty()){
            //test si lE Fournisseur est valid avant l'insertion dans la bd
            log.error("Fournisseur is not valid{}", dto);
            throw new InvalidEntityException("lE Fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_FOUND, errors);
        }
        Fournisseur savedFournisseur = fournisseurRepository.save(FournisseurDto.toEntity(dto));
        return FournisseurDto.fromEntity(savedFournisseur);
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
            return null;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return Optional.of(FournisseurDto.fromEntity(fournisseur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Fournisseur avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public FournisseurDto findByNom(String nom) {
        if(nom == null){
            log.error("Fournisseur name is null");
            return null;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findFournisseurByNom(nom);
        return Optional.of(FournisseurDto.fromEntity(fournisseur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Fournisseur avec le nom =" + nom + "n'a été trouvé dans la BD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND)
        );
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
            return ;
        }
        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un fournisseurs qui a déjà des commandes", ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }
        fournisseurRepository.deleteById(id);
    }
}

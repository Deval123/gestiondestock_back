package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.EntrepriseDto;
import com.deval.gestiondestock.dto.RolesDto;
import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.Entreprise;
import com.deval.gestiondestock.model.Utilisateur;
import com.deval.gestiondestock.repository.EntrepriseRepository;
import com.deval.gestiondestock.repository.RolesRepository;
import com.deval.gestiondestock.services.EntrepriseService;
import com.deval.gestiondestock.services.UtilisateurService;
import com.deval.gestiondestock.validator.EntrepriseValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService, RolesRepository rolesRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if(!errors.isEmpty()){
            //test si l'entreprise est valid avant l'insertion dans la bd
            log.error("Entreprise is not valid {}", dto);
            throw new InvalidEntityException("l'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(entrepriseRepository.save(EntrepriseDto.toEntity(dto)));

        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);
        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto dto){
        return UtilisateurDto.builder()
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .email(dto.getEmail())
                .numTel(dto.getNumTel())
                .motDePasse(generateRandomPassword())
                .entreprise(dto)
                .dateDeNaissance(Instant.now())
                .photo(dto.getPhoto())
                .build();
    }


    private String generateRandomPassword(){
        return "deval123@$$luna";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if(id == null){
            log.error("Entreprise ID is null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune entreprise avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
        );
    }

    @Override
    @RequestMapping(value = "/{nom}", method = RequestMethod.GET)
    public EntrepriseDto findByNom(@PathVariable(value = "nom") String nom) {
        if(nom == null){
            log.error("Entreprise NAME is null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findEntrepriseByNom(nom);
        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune entreprise avec le nom=" + nom + "n'a été trouvé dans la BD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
        );
    }

    @Override
    @RequestMapping(value = "/{codefiscal}", method = RequestMethod.GET)
    public EntrepriseDto findByCodeFiscal(@PathVariable(value = "codefiscal") String codefiscal) {
        if(codefiscal == null){
            log.error("Entreprise code fiscal is null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findEntrepriseByCodeFiscal(codefiscal);
        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune entreprise avec le Code Fiscal=" + codefiscal + "n'a été trouvé dans la BD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
        );

    }

    @Override
    @RequestMapping(value = "/{tel}", method = RequestMethod.GET)
    public EntrepriseDto findByNumTel(@PathVariable(value = "tel") String tel) {
        if(tel == null){
            log.error("Entreprise phone number is null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findEntrepriseByNumTel(tel);
        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune entreprise avec le numéro =" + tel + "n'a été trouvé dans la BD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
        );
    }



    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Entreprise ID is null");
            return ;
        }
        entrepriseRepository.deleteById(id);
    }
}

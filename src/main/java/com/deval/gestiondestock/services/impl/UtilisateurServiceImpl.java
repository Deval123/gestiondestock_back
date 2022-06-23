package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.deval.gestiondestock.dto.MvtStockDto;
import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.MvtStock;
import com.deval.gestiondestock.model.Utilisateur;
import com.deval.gestiondestock.repository.UtilisateurRepository;
import com.deval.gestiondestock.services.UtilisateurService;
import com.deval.gestiondestock.validator.MvtStockValidator;
import com.deval.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Utilisateur is not valid {}", dto);
            throw new InvalidEntityException("L'utilisateur' n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }

        if(userAlreadyExists(dto.getEmail())){
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe déjà !!", ErrorCodes.UTILISATEUR_ALREADY_EXIT, errors);
            //Collections.singletonList("Un autre utilisateur avec le même email existe déjà dans la base de donnée.");
        }

        Utilisateur savedUtilisateur = utilisateurRepository.save(UtilisateurDto.toEntity(dto));
        return UtilisateurDto.fromEntity(savedUtilisateur);

    }

    private boolean userAlreadyExists(String email) {
        Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByEmail(email);
        return user.isPresent();
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if(id == null){
            log.error("Utilisateur ID is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public UtilisateurDto findByNom(String nom) {
        if(nom == null){
            log.error("Utilisateur name is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByNom(nom);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec le nom =" + nom + "n'a été trouvé dans la BD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public UtilisateurDto findByNumTel(String numTel) {
        if(numTel == null){
            log.error("Utilisateur name is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByNom(numTel);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec le numéro =" + numTel + "n'a été trouvé dans la BD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                "Aucun Utilisateur avec le mail =" + email + "n'a été trouvé dans la BD",ErrorCodes.UTILISATEUR_NOT_FOUND
        ));

/*        if(email == null){
            log.error("Utilisateur email is null");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Utilisateur avec le mail =" + email + "n'a été trouvé dans la BD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
        );*/
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Utilisateur ID is null");
            return ;
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()){
            log.warn("Aucun utilisateur n'a été trouvé avec l'ID =" + dto.getId() + " dans la base de donnée");
            throw new EntityNotFoundException("Aucun utilisateur n'a été trouvé avec l'ID =" + dto.getId() + "dans la base de donnée",
                    ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(dto.getMotDePasse());
        return UtilisateurDto.fromEntity(utilisateurRepository.save(utilisateur));
    }

    private void validate(ChangerMotDePasseUtilisateurDto dto){

        if (dto == null){
            log.warn("Impossible de modifier le mot de passe avec un objet null");
            throw new InvalidOperationException("Aucune information n'a été fournit pour pouvoir changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null){
            log.warn("Impossible de modifier le mot de passe avec un ID utilisateur null");
            throw new InvalidOperationException("ID utilisateur null :: Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec un mot de passe utilisateur | NULL");
            throw new InvalidOperationException("Mot de passe utilisateur null :: Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe différents");
            throw new InvalidOperationException("Mot de passe utilisateur non conformes :: Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }

}

package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.*;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.*;
import com.deval.gestiondestock.repository.ArticleRepository;
import com.deval.gestiondestock.repository.LigneVenteRepository;
import com.deval.gestiondestock.repository.VentesRepository;
import com.deval.gestiondestock.services.MvtStockService;
import com.deval.gestiondestock.services.VentesService;
import com.deval.gestiondestock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;

    private MvtStockService mvtStockService;


    @Autowired
    public VentesServiceImpl(ArticleRepository articleRepository,
     VentesRepository ventesRepository, LigneVenteRepository ligneVenteRepository, MvtStockService mvtStockService) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStockService = mvtStockService;
    }


    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VentesValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Ventes is not valid", dto);
            throw new InvalidEntityException("l'objet vente' n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        List<String> articleErrors = new ArrayList<>();

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
        if (article.isEmpty()){
            articleErrors.add("Aucun article avec l'ID" + ligneVenteDto.getArticle().getId() +"n'a été trouvé dans la BD");
        }
        });

        if (!articleErrors.isEmpty()){
            log.error("One or more articles were not found in the database, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas été trouvé dans la BD", ErrorCodes.VENTE_NOT_VALID, errors);
        }


        Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVentes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStock(ligneVente);
        });
        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findById(Integer id) {
        if(id == null){
            log.error("Utilisateur ID is null");
            return null;
        }
        Optional<Ventes> ventes = ventesRepository.findById(id);
        return Optional.of(VentesDto.fromEntity(ventes.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Ventes avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.VENTE_NOT_FOUND)
        );
    }

    @Override
    public VentesDto findByDateVente(Instant dateVente) {
        if(dateVente == null){
            log.error("Date Vente ID is null");
            return null;
        }
        Optional<Ventes> ventes = ventesRepository.findVentesByDateVente(dateVente);
        return Optional.of(VentesDto.fromEntity(ventes.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Ventes avec la date devVente suivante : =" + dateVente + "n'a été trouvé dans la BD",
                        ErrorCodes.VENTE_NOT_FOUND)
        );
    }

    @Override
    public VentesDto findByCode(String code) {
        if(code == null){
            log.error("code ID is null");
            return null;
        }
        Optional<Ventes> ventes = ventesRepository.findVentesByCode(code);
        return Optional.of(VentesDto.fromEntity(ventes.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Ventes avec le code : =" + code + "n'a été trouvé dans la BD",
                        ErrorCodes.VENTE_NOT_FOUND)
        );
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une vente . . .", ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        ventesRepository.deleteById(id);
    }

    private void updateMvtStock(LigneVente lig){
            MvtStockDto mvtStockDto = MvtStockDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStock.SORTIE)
                    .sourceMvtStock(SourceMvtStock.VENTE)
                    .quantite(lig.getQuantite())
                    .identreprise(lig.getIdentreprise())
                    .build();
            mvtStockService.sortieStock(mvtStockDto);
    }
}

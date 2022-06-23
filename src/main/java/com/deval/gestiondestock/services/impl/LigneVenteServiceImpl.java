package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.LigneVenteDto;
import com.deval.gestiondestock.dto.VentesDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.LigneVente;
import com.deval.gestiondestock.repository.LigneVenteRepository;
import com.deval.gestiondestock.services.LigneVenteService;
import com.deval.gestiondestock.validator.ArticleValidator;
import com.deval.gestiondestock.validator.LigneVenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LigneVenteServiceImpl implements LigneVenteService {

    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    public LigneVenteServiceImpl(LigneVenteRepository ligneVenteRepository){
        this.ligneVenteRepository = ligneVenteRepository;
    }

    @Override
    public LigneVenteDto save(LigneVenteDto dto) {
        List<String> errors = LigneVenteValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Ligne Vente is not valid{}", dto);
            throw new InvalidEntityException("Ligne Vente n'est pas valide", ErrorCodes.LIGNE_VENTE_NOT_FOUND, errors);
        }
        LigneVente savedLigneVente = ligneVenteRepository.save(LigneVenteDto.toEntity(dto));
        return LigneVenteDto.fromEntity(savedLigneVente);

    }

    @Override
    public LigneVenteDto findById(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return null;
        }
        Optional<LigneVente> ligneVente = ligneVenteRepository.findById(id);
        return Optional.of(LigneVenteDto.fromEntity(ligneVente.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune ligne de vente avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.LIGNE_VENTE_NOT_FOUND)
        );
    }

    @Override
    public List<LigneVenteDto> findAll() {
        return ligneVenteRepository.findAll().stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Cette ligne de vente est nulle");
            return ;
        }
        ligneVenteRepository.deleteById(id);
    }



}

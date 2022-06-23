package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.MvtStockDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.MvtStock;
import com.deval.gestiondestock.model.TypeMvtStock;
import com.deval.gestiondestock.repository.MvtStockRepository;
import com.deval.gestiondestock.services.ArticleService;
import com.deval.gestiondestock.services.MvtStockService;
import com.deval.gestiondestock.validator.ArticleValidator;
import com.deval.gestiondestock.validator.MvtStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStockServiceImpl implements MvtStockService {

    private MvtStockRepository mvtStockRepository;
    private ArticleService articleService;

    @Autowired
    public MvtStockServiceImpl(MvtStockRepository mvtStockRepository, ArticleService articleService){
        this.mvtStockRepository = mvtStockRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if(idArticle == null){
            log.warn("ID article es null");
            return BigDecimal.valueOf(-1);

        }
        articleService.findById(idArticle);
        return mvtStockRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStockDto> mvtStockArticle(Integer idArticle) {
        return mvtStockRepository.findAllByArticleId(idArticle).stream()
                .map(MvtStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStockDto entreeStock(MvtStockDto dto) {
        return entreePositive(dto, TypeMvtStock.ENTREE);
    }

    @Override
    public MvtStockDto sortieStock(MvtStockDto dto) {
        return sortieNegative(dto, TypeMvtStock.SORTIE);
    }

    @Override
    public MvtStockDto correctionStockPos(MvtStockDto dto) {
        return entreePositive(dto, TypeMvtStock.CORRECTION_POS);
    }

    @Override
    public MvtStockDto correctionStockNeg(MvtStockDto dto) {
        return sortieNegative(dto, TypeMvtStock.CORRECTION_NEG);
    }


    private  MvtStockDto entreePositive(MvtStockDto dto, TypeMvtStock typeMvtStock){
        List<String> errors = MvtStockValidator.validate(dto);
        if(!errors.isEmpty()){
            log.warn("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvvement de stock n'est pas valid", ErrorCodes.MVT_STOCK_NOT_VALID, errors);
        }
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue())));
        dto.setTypeMvt(typeMvtStock);
        return MvtStockDto.fromEntity(mvtStockRepository.save(MvtStockDto.toEntity(dto)));
    }

    private MvtStockDto sortieNegative(MvtStockDto dto, TypeMvtStock typeMvtStock){
        List<String> errors = MvtStockValidator.validate(dto);
        if(!errors.isEmpty()){
            log.warn("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvvement de stock n'est pas valid", ErrorCodes.MVT_STOCK_NOT_VALID, errors);
        }
        dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue())* -1));
        //-1 pour être sur que la sortie de stock est toujours négative
        dto.setTypeMvt(typeMvtStock);
        return MvtStockDto.fromEntity(mvtStockRepository.save(MvtStockDto.toEntity(dto)));
    }



/*    @Override
    public MvtStockDto save(MvtStockDto dto) {
        List<String> errors = MvtStockValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("MvtStock is not valid{}", dto);
            throw new InvalidEntityException("le Mvt de Stock n'est pas valide", ErrorCodes.MVT_STOCK_NOT_FOUND, errors);
        }
        MvtStock savedMvtStock = mvtStockRepository.save(MvtStockDto.toEntity(dto));
        return MvtStockDto.fromEntity(savedMvtStock);

    }

    @Override
    public MvtStockDto findById(Integer id) {
        if(id == null){
            log.error("MvtStock ID is null");
            return null;
        }
        Optional<MvtStock> mvtStock = mvtStockRepository.findById(id);
        return Optional.of(MvtStockDto.fromEntity(mvtStock.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun MvtSt de sock avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.MVT_STOCK_NOT_FOUND)
        );
    }


    @Override
    public MvtStockDto findByDateMvt(Instant dateMvt) {
        if(dateMvt == null){
            log.error("Date Mvt is null");
            return null;
        }
        Optional<MvtStock> mvtStock = mvtStockRepository.findMvtStockByDateMvt(dateMvt);
        return Optional.of(MvtStockDto.fromEntity(mvtStock.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun MvtSt de sock avec la Date =" + dateMvt + "n'a été trouvé dans la BD",
                        ErrorCodes.MVT_STOCK_NOT_FOUND)
        );
    }

    @Override
    public MvtStockDto findByArticle(Article article) {
        if(article == null){
            log.error("Date Mvt is null");
            return null;
        }
        Optional<MvtStock> mvtStock = mvtStockRepository.findMvtStockByArticle(article);
        return Optional.of(MvtStockDto.fromEntity(mvtStock.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun MvtSt de sock avec l'article' =" + article + "n'a été trouvé dans la BD",
                        ErrorCodes.MVT_STOCK_NOT_FOUND)
        );
    }


    @Override
    public List<MvtStockDto> findAll() {
        return mvtStockRepository.findAll().stream()
                .map(MvtStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("MvtStock ID is null");
            return ;
        }
        mvtStockRepository.deleteById(id);
    }*/
}

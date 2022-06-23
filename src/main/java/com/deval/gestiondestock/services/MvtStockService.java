package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.MvtStockDto;
import com.deval.gestiondestock.model.Article;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface MvtStockService {
    BigDecimal stockReelArticle(Integer idArticle);

    List<MvtStockDto> mvtStockArticle(Integer idArticle);

    MvtStockDto entreeStock(MvtStockDto dto);

    MvtStockDto sortieStock(MvtStockDto dto);

    MvtStockDto correctionStockPos(MvtStockDto dto);

    MvtStockDto correctionStockNeg(MvtStockDto dto);




/*    MvtStockDto save(MvtStockDto dto);

    MvtStockDto findById(Integer id);

    MvtStockDto findByDateMvt(Instant dateMvt);

    MvtStockDto findByArticle(Article article);

    //MvtStockDto findByArticle(String typeMvt);

    List<MvtStockDto> findAll();

    void delete(Integer id);*/

}

package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.MvtStockApi;
import com.deval.gestiondestock.dto.MvtStockDto;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.services.MvtStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
public class MvtStockController implements MvtStockApi {
    @Autowired
    private MvtStockService mvtStockService;

    @Autowired
    public MvtStockController(MvtStockService mvtStockService){
        this.mvtStockService = mvtStockService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return mvtStockService.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStockDto> mvtStockArticle(Integer idArticle) {
        return mvtStockService.mvtStockArticle(idArticle);
    }

    @Override
    public MvtStockDto entreeStock(MvtStockDto dto) {
        return mvtStockService.entreeStock(dto);
    }

    @Override
    public MvtStockDto sortieStock(MvtStockDto dto) {
        return mvtStockService.sortieStock(dto);
    }

    @Override
    public MvtStockDto correctionStockPos(MvtStockDto dto) {
        return mvtStockService.correctionStockPos(dto);
    }

    @Override
    public MvtStockDto correctionStockNeg(MvtStockDto dto) {
        return mvtStockService.correctionStockNeg(dto);
    }

/*    @Override
    public MvtStockDto save(MvtStockDto dto) {
        return mvtStockService.save(dto);
    }

    @Override
    public MvtStockDto findById(Integer id) {
        return mvtStockService.findById(id);
    }

    @Override
    public MvtStockDto findByDateMvt(Instant dateMvt) {
        return mvtStockService.findByDateMvt(dateMvt);
    }

    @Override
    public MvtStockDto findByArticle(Article article) {
        return mvtStockService.findByArticle(article);
    }

    @Override
    public List<MvtStockDto> findAll() {
        return mvtStockService.findAll();
    }

    @Override
    public void delete(Integer id) {
        mvtStockService.delete(id);
    }*/
}

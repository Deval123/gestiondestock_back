package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.MvtStockDto;
import com.deval.gestiondestock.model.Article;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/mvtStock")
public interface MvtStockApi {

    @GetMapping(value = APP_ROOT + "/mvtStock/stock/réel/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    BigDecimal stockReelArticle(@PathVariable("idArticle")  Integer idArticle);

    @GetMapping(value = APP_ROOT + "/mvtStock/filtre/article/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStockDto> mvtStockArticle(@PathVariable("idArticle")  Integer idArticle);

    @PostMapping(value = APP_ROOT + "/mvtStock/entrée", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto entreeStock(@RequestBody  MvtStockDto dto);


    @PostMapping(value = APP_ROOT + "/mvtStock/sortie", produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto sortieStock(@RequestBody MvtStockDto dto);

    @PostMapping(value = APP_ROOT + "/mvtStock/correctionPos", produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto correctionStockPos(@RequestBody MvtStockDto dto);


    @PostMapping(value = APP_ROOT + "/mvtStock/correctionNeg", produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto correctionStockNeg(@RequestBody MvtStockDto dto);





/*    @PostMapping(value = APP_ROOT + "/mvtStock/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto save(@RequestBody MvtStockDto dto);

    @GetMapping(value = APP_ROOT + "/mvtStock/id", produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto findById(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/mvtStock/{dateMvt}", produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto findByDateMvt(@PathVariable("dateMvt") Instant dateMvt);

    @GetMapping(value = APP_ROOT + "/mvtStock/{article}", produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStockDto findByArticle(@PathVariable("article") Article article);

    @GetMapping(value = APP_ROOT + "/mvtStock/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MvtStockDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/mvtStock/delete/{id}")
    void delete(@PathVariable("id") Integer id);*/

}

package com.deval.gestiondestock.dto;


import com.deval.gestiondestock.model.MvtStock;
import com.deval.gestiondestock.model.SourceMvtStock;
import com.deval.gestiondestock.model.TypeMvtStock;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStockDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ArticleDto article;

    private TypeMvtStock typeMvt;

    private  Integer identreprise;

    private SourceMvtStock sourceMvtStock;

    public static MvtStockDto fromEntity(MvtStock mvtStock){
        if(mvtStock == null){
            return null;
        }
        return MvtStockDto.builder()
                .id(mvtStock.getId())
                .dateMvt(mvtStock.getDateMvt())
                .quantite(mvtStock.getQuantite())
                .article(ArticleDto.fromEntity(mvtStock.getArticle()))
                .typeMvt(mvtStock.getTypeMvt())
                .sourceMvtStock(mvtStock.getSourceMvt())
                .identreprise(mvtStock.getIdentreprise())
                .build();
    }

    public static MvtStock toEntity(MvtStockDto mvtStockDto){
        if(mvtStockDto == null){
            return null;
        }

        MvtStock mvtStock = new MvtStock();
        mvtStock.setId(mvtStockDto.getId());
        mvtStock.setDateMvt(mvtStockDto.getDateMvt());
        mvtStock.setQuantite(mvtStockDto.getQuantite());
        mvtStock.setArticle(ArticleDto.toEntity(mvtStockDto.getArticle()));
        mvtStock.setTypeMvt(mvtStockDto.getTypeMvt());
        mvtStock.setSourceMvt(mvtStockDto.getSourceMvtStock());
        mvtStock.setIdentreprise(mvtStockDto.getIdentreprise());
        return mvtStock;
    }
}

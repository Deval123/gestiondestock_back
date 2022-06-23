package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.deval.gestiondestock.dto.LigneVenteDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.LigneCommandeClient;
import com.deval.gestiondestock.model.LigneCommandeFournisseur;
import com.deval.gestiondestock.model.LigneVente;
import com.deval.gestiondestock.repository.*;
import com.deval.gestiondestock.services.ArticleService;
import com.deval.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
private ArticleRepository articleRepository;
private LigneVenteRepository ventesRepository;
private LigneCommandeFournisseurRepository commandeFournisseurRepository;
private LigneCommandeClientRepository commandeClientRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository ventesRepository,
      LigneCommandeFournisseurRepository commandeFournisseurRepository, LigneCommandeClientRepository commandeClientRepository) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
    List<String> errors = ArticleValidator.validate(dto);
    if(!errors.isEmpty()){
        //test si l'article est valid avant l'insertion dans la bd
        log.error("Article is not valid{}", dto);
        throw new InvalidEntityException("l'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
    }
        Article savedArticle = articleRepository.save(ArticleDto.toEntity(dto));
        return ArticleDto.fromEntity(savedArticle);

        //ou encore return ArticleDto.fromEntity(articleRepository.save(ArticleDto.toEntity(dto)));
    }

    @Override
    public ArticleDto findById(Integer id) {
    if(id == null){
        log.error("Article ID is null");
        return null;
    }
    Optional<Article>  article = articleRepository.findById(id);
        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)) {
            log.error("Article code Article is null");
            return null;
        }
        Optional<Article>  article = articleRepository.findArticleByCodeArticle(codeArticle);
        return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec le code =" + codeArticle + "n'a été trouvé dans la BD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return ventesRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return ;
        }
        List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans des commandes fournisseur", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneVente> ligneVentes = ventesRepository.findAllByArticleId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans les ventes", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        articleRepository.deleteById(id);
    }
}

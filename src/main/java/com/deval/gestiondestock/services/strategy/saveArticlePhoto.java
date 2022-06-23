package com.deval.gestiondestock.services.strategy;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.services.ArticleService;
import com.deval.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;


@Service("articleStrategy")
@Slf4j
public class saveArticlePhoto implements Strategy<ArticleDto> {

    private FlickrService flickrService;
    private ArticleService articleService;

    @Autowired
    public saveArticlePhoto(FlickrService flickrService, ArticleService articleService) {
        this.flickrService = flickrService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDto articleDto = articleService.findById(id);
        String urlPhoto = flickrService.savedPhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDto.setPhoto(urlPhoto);
        return articleService.save(articleDto);
    }
}

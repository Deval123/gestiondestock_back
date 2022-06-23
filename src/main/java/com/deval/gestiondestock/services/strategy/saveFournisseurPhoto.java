package com.deval.gestiondestock.services.strategy;

import com.deval.gestiondestock.dto.FournisseurDto;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Fournisseur;
import com.deval.gestiondestock.services.FlickrService;
import com.deval.gestiondestock.services.FournisseurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("fournisseurStrategy")
@Slf4j
public class saveFournisseurPhoto implements Strategy<FournisseurDto> {
    private FlickrService flickrService;
    private FournisseurService fournisseurService;

    @Autowired
    public saveFournisseurPhoto(FlickrService flickrService, FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        FournisseurDto fournisseurDto = fournisseurService.findById(id);
        String urlPhoto = flickrService.savedPhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseurDto.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseurDto);
    }
}

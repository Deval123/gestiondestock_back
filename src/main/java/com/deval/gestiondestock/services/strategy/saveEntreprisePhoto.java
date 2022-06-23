package com.deval.gestiondestock.services.strategy;

import com.deval.gestiondestock.dto.EntrepriseDto;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Entreprise;
import com.deval.gestiondestock.services.EntrepriseService;
import com.deval.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("entrepriseStrategy")
@Slf4j
public class saveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private FlickrService flickrService;
    EntrepriseService entrepriseService;

    @Autowired
    public saveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entrepriseDto = entrepriseService.findById(id);
        String urlPhoto = flickrService.savedPhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entrepriseDto.setPhoto(urlPhoto);
        return entrepriseService.save(entrepriseDto);
    }
}

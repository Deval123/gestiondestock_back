package com.deval.gestiondestock.services.strategy;

import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Utilisateur;
import com.deval.gestiondestock.services.FlickrService;
import com.deval.gestiondestock.services.UtilisateurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("utilisateurStrategy")
@Slf4j
public class saveUtilisateurPhoto implements Strategy<UtilisateurDto> {
    private FlickrService flickrService;
    private UtilisateurService utilisateurService;

    @Autowired
    public saveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateurDto = utilisateurService.findById(id);
        String urlPhoto = flickrService.savedPhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        utilisateurDto.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateurDto);
    }
}

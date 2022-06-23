package com.deval.gestiondestock.services.strategy;

import com.deval.gestiondestock.dto.ClientDto;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Client;
import com.deval.gestiondestock.services.ClientService;
import com.deval.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class saveClientPhoto implements Strategy<ClientDto> {

    private FlickrService flickrService;
    private ClientService clientService;

    @Autowired
    public saveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ClientDto clientDto = clientService.findById(id);
        String urlPhoto = flickrService.savedPhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clientDto.setPhoto(urlPhoto);
        return clientService.save(clientDto);
    }
}

package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.CategoryDto;
import com.deval.gestiondestock.dto.ClientDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.Category;
import com.deval.gestiondestock.model.Client;
import com.deval.gestiondestock.model.CommandeClient;
import com.deval.gestiondestock.repository.CategoryRepository;
import com.deval.gestiondestock.repository.ClientRepository;
import com.deval.gestiondestock.repository.CommandeClientRepository;
import com.deval.gestiondestock.services.ClientService;
import com.deval.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository) {
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            //test si le Client est valid avant l'insertion dans la bd
            log.error("Client is not valid{}", dto);
            throw new InvalidEntityException("le Client  n'est pas valide", ErrorCodes.CLIENT_NOT_FOUND, errors);
        }
        Client savedClient = clientRepository.save(ClientDto.toEntity(dto));
        return ClientDto.fromEntity(savedClient);

        //ou encore return ArticleDto.fromEntity(articleRepository.save(ArticleDto.toEntity(dto)));    }
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id == null){
            log.error("Client ID is null");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Client avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.CLIENT_NOT_FOUND)
        );    }

    @Override
    public ClientDto findByNom(String nom) {
        if(!StringUtils.hasLength(nom)) {
            log.error("Client NAME is null");
            return null;
        }
        Optional<Client>  client = clientRepository.findClientByNom(nom);
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Client avec le code =" + nom + "n'a été trouvé dans la BD",
                        ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public ClientDto findByMail(String mail) {
        if(!StringUtils.hasLength(mail)) {
            log.error("Client Mail is null");
            return null;
        }
        Optional<Client>  client = clientRepository.findClientByMail(mail);
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Client avec le mail =" + mail + "n'a été trouvé dans la BD",
                        ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public ClientDto findByNumTel(String tel) {
        if(!StringUtils.hasLength(tel)) {
            log.error("Client Mail is null");
            return null;
        }
        Optional<Client>  client = clientRepository.findClientByNumTel(tel);
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Client avec le mail =" + tel + "n'a été trouvé dans la BD",
                        ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return ;
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un client qui a déjà des commandes clients", ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        clientRepository.deleteById(id);
    }

}

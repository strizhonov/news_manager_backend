package com.epam.lab.controller;

import com.epam.lab.dto.ClientDto;
import com.epam.lab.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    /**
     * Save Client into the database.
     * <p>
     * A Client id value will not be considered during the operation.
     * <p>
     * If the same entity does already present, there is no changes performed.
     * <p>
     * If Author is successfully added or the same entity does already present,
     * user gets response code "200". Otherwise user gets a code number that
     * corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToSave to insert into the database
     * @return created author
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PostMapping
    public ClientDto save(@RequestBody @Valid final ClientDto dtoToSave) {
        return service.create(dtoToSave);
    }

}

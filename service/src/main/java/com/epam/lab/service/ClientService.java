package com.epam.lab.service;

import com.epam.lab.dto.ClientDto;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClientService extends AppService<ClientDto>, ClientDetailsService {
}

package com.epam.lab.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "client")
public class Client implements ClientDetails {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String clientId;

    @Column(name = "secret")
    private String clientSecret;

    @Column(name = "grant_type")
    private String authorizedGrantType;

    public Client() {
    }

    public void setAuthorizedGrantType(final String authorizedGrantType) {
        this.authorizedGrantType = authorizedGrantType;
    }

    public String getAuthorizedGrantType() {
        return authorizedGrantType;
    }

    @Override
    public boolean isAutoApprove(final String s) {
        return true;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public Set<String> getScope() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return new HashSet<>(Collections.singletonList(authorizedGrantType));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getResourceIds() {
        return new HashSet<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 60 * 60 * 12;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 60 * 60 * 12 * 2;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }


}

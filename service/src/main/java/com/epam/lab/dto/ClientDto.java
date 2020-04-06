package com.epam.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ClientDto {

    @NotNull
    @Size(max = 30)
    private String clientId;

    @NotNull
    @Size(max = 20)
    private String clientSecret;

    @NotNull
    private GrantType grantType;

    public ClientDto() {
        // Empty bean constructor
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(final GrantType grantType) {
        this.grantType = grantType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClientDto clientDto = (ClientDto) o;
        return Objects.equals(clientId, clientDto.clientId) &&
                Objects.equals(clientSecret, clientDto.clientSecret) &&
                grantType == clientDto.grantType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientSecret, grantType);
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", grantType=" + grantType +
                '}';
    }

    public enum GrantType {

        @JsonProperty("password")
        RESOURCE_OWNER("password"),

        @JsonProperty("implicit")
        IMPLICIT("implicit");

        private String value;

        GrantType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static GrantType fromString(final String value) {
            return Stream.of(GrantType.values())
                    .filter(element -> element.name().equalsIgnoreCase(value))
                    .findFirst().orElseThrow(
                            () -> new NullPointerException(String.format("GrantType with [%s] value not found", value)));
        }

    }
}

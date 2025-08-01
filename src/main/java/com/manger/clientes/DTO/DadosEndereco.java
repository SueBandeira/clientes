package com.manger.clientes.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEndereco (@NotBlank String cep,
                             @NotBlank String logradouro,
                             @NotBlank String complemento,
                             @NotBlank String bairro,
                             @NotBlank @JsonAlias("localidade") String cidade,
                             @NotBlank String uf,
                             @NotBlank String estado) {
}
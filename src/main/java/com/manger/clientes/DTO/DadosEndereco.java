package com.manger.clientes.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEndereco (String cep, String logradouro,
                             String complemento, String bairro,
                             @JsonAlias("localidade") String cidade, String uf, String estado) {
}
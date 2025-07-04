package com.manger.clientes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEndereco (String rua, String numero, String bairro, String cidade, String estado) {
}

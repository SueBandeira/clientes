package com.manger.clientes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public <T> T obterDados(String json, Class<T> classe) {
    if (json == null || json.isEmpty()) {
      throw new IllegalArgumentException("O JSON fornecido está vazio ou é nulo.");
    }

    try {
      return mapper.readValue(json, classe);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao processar o JSON: " + e.getMessage(), e);
    }
  }
}



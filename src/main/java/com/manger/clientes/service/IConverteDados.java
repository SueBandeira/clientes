package com.manger.clientes.service;

public interface IConverteDados {
  <T> T obterDados(String json, Class<T> classe);
}

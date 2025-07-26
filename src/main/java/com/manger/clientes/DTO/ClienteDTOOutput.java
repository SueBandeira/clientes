package com.manger.clientes.DTO;

import com.manger.clientes.model.Cliente;

public record ClienteDTOOutput(String nome, String telefone, String email) {

  public ClienteDTOOutput(Cliente cliente1) {
    this(cliente1.getNome(), cliente1.getTelefone(), cliente1.getEmail());
  }
}

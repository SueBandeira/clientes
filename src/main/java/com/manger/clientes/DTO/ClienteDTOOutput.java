package com.manger.clientes.DTO;

import com.manger.clientes.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTOOutput(@NotBlank String nome,
                               @NotBlank String telefone,
                               @Email String email,
                               Endereco endereco) {

  public ClienteDTOOutput(Cliente cliente1) {
    this(cliente1.getNome(), cliente1.getTelefone(), cliente1.getEmail(), cliente1.getEndereco());
  }
}

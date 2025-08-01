package com.manger.clientes.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOCriaCliente(@NotBlank String nome,
                             @NotBlank String telefone,
                             @Email String email,
                             @NotBlank String cep) {
}

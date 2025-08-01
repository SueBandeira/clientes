package com.manger.clientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DTOCriaUsuario(@NotBlank String nome, @Size(min =3) String senha) {
}
